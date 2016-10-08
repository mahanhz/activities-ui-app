package com.amhzing.activities.ui.participant;

import com.amhzing.activities.ui.participant.mapping.Participants;
import com.amhzing.activities.ui.participant.response.ErrorResponse;
import com.amhzing.activities.ui.participant.response.ParticipantResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.atlassian.fugue.Either;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.amhzing.activities.ui.participant.Failure.SYSTEM_IS_DOWN;
import static com.amhzing.activities.ui.participant.Failure.SYSTEM_RETURNED_ERRORS;
import static io.atlassian.fugue.Either.left;
import static io.atlassian.fugue.Either.right;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.Assert.notNull;

public class CircuitBreakingParticipantService implements ParticipantService<Failure, Participants> {

    private static final String GROUP_KEY = "participantsGroupKey";
    private static final String COMMAND_KEY_BY_CRITERIA = "participantsByCriteria";
    private static final String FALLBACK = "fallback";

    private RestTemplate restTemplate;
    private String participantUrl;

    public CircuitBreakingParticipantService(final RestTemplate restTemplate, final String participantUrl) {
        this.restTemplate = notNull(restTemplate);
        this.participantUrl = participantUrl;
    }

    @Override
    @HystrixCommand(groupKey = GROUP_KEY, commandKey = COMMAND_KEY_BY_CRITERIA, fallbackMethod = FALLBACK)
    public Either<Failure, Participants> participantsByCriteria(final QueryCriteria queryCriteria) {
        notNull(queryCriteria);

        // TODO - This should be expanded to pass all wuery criteria to participant url
        final String url = participantUrl + "/query/" + queryCriteria.getCountry();
        final ParticipantResponse response = restTemplate.getForObject(url,
                                                                       ParticipantResponse.class);

        if (hasErrors(response.getErrors())) {
            return left(SYSTEM_RETURNED_ERRORS);
        }

        final Participants participants = response.getParticipants()
                                                  .stream()
                                                  .map(ParticipantFactory::createParticipant)
                                                  .collect(collectingAndThen(toList(), Participants::create));

        return right(participants);
    }

    public Either<Failure, Participants> fallback(final QueryCriteria queryCriteria, final Throwable e) {
        return left(SYSTEM_IS_DOWN);
    }

    private boolean hasErrors(final List<ErrorResponse> errors) {
        final long errorCount = errors.stream().filter(error -> !error.isEmpty()).count();

        return errorCount > 0;
    }
}
