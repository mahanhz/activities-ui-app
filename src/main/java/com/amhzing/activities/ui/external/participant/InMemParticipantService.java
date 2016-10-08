package com.amhzing.activities.ui.external.participant;

import com.amhzing.activities.ui.external.participant.response.ParticipantResponse;
import com.amhzing.activities.ui.query.data.participant.Failure;
import com.amhzing.activities.ui.query.data.participant.ParticipantService;
import com.amhzing.activities.ui.query.data.participant.QueryCriteria;
import com.amhzing.activities.ui.query.data.participant.mapping.Participants;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.atlassian.fugue.Either;

import java.io.IOException;

import static com.amhzing.activities.ui.query.data.participant.Failure.INTERNAL_ERROR;
import static io.atlassian.fugue.Either.left;
import static io.atlassian.fugue.Either.right;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

public class InMemParticipantService implements ParticipantService<Failure, Participants> {

    @Override
    public Either<Failure, Participants> participantsByCriteria(final QueryCriteria queryCriteria) {
        notNull(queryCriteria);

        try {
            return right(participants());
        } catch (IOException ioEx) {
            return left(INTERNAL_ERROR);
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
