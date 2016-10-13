package com.amhzing.activities.ui.infra.participant;

import com.amhzing.activities.ui.domain.participant.repository.QueryCriteria;
import com.amhzing.activities.ui.external.participant.response.ParticipantResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.uuid.Generators;
import io.atlassian.fugue.Either;

import java.io.IOException;
import java.util.UUID;

import static com.amhzing.activities.ui.infra.participant.Failure.INTERNAL_ERROR;
import static io.atlassian.fugue.Either.left;
import static io.atlassian.fugue.Either.right;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

public class InMemParticipantService implements DefaultParticipantService {

    @Override
    public Either<CorrelatedFailure, Participants> participantsByCriteria(final QueryCriteria queryCriteria) {
        notNull(queryCriteria);

        try {
            return right(participants());
        } catch (IOException ioEx) {
            final UUID errorId = Generators.timeBasedGenerator().generate();
            return left(CorrelatedFailure.create(INTERNAL_ERROR, errorId.toString()));
        }
    }

    private Participants participants() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final ClassLoader classLoader = getClass().getClassLoader();

        final ParticipantResponse response = objectMapper.readValue(classLoader.getResource("participants.json"),
                                                                    ParticipantResponse.class);

        return response.getParticipants()
                       .stream()
                       .map(ParticipantFactory::createParticipant)
                       .collect(collectingAndThen(toList(), Participants::create));
    }
}
