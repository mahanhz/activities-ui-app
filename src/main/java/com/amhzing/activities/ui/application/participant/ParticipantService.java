package com.amhzing.activities.ui.application.participant;

import com.amhzing.activities.ui.application.exception.FailureException;
import com.amhzing.activities.ui.domain.participant.model.Participant;
import com.amhzing.activities.ui.domain.participant.repository.QueryCriteria;
import com.amhzing.activities.ui.infra.participant.CorrelatedFailure;
import com.amhzing.activities.ui.infra.participant.DefaultParticipantService;
import com.amhzing.activities.ui.infra.participant.Participants;
import io.atlassian.fugue.Either;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class ParticipantService {

    private DefaultParticipantService participantService;

    public ParticipantService(final DefaultParticipantService participantService) {
        this.participantService = notNull(participantService);
    }

    public List<Participant> getParticipants(final QueryCriteria queryCriteria) {
        notNull(queryCriteria);

        final Either<CorrelatedFailure, Participants> result = participantService.participantsByCriteria(queryCriteria);

        if (result.isRight()) {
            final Participants participants = result.right().get();
            return participants.getParticipants();
        } else {
            final CorrelatedFailure failure = result.left().get();
            throw new FailureException("An error occurred", failure.getFailure().toString(), failure.getErrorId());
        }
    }
}
