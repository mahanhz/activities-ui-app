package com.amhzing.activities.ui.infra;

import com.amhzing.activities.ui.application.participant.DefaultParticipantService;
import com.amhzing.activities.ui.application.Failure;
import com.amhzing.activities.ui.application.participant.Participants;
import com.amhzing.activities.ui.application.participant.QueryCriteria;
import com.amhzing.activities.ui.external.participant.ExternalParticipantService;
import com.amhzing.activities.ui.external.participant.request.SearchSpecification;
import com.amhzing.activities.ui.external.participant.response.ErrorResponse;
import com.amhzing.activities.ui.external.participant.response.ParticipantResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.atlassian.fugue.Either;

import java.util.List;

import static com.amhzing.activities.ui.application.Failure.SYSTEM_IS_DOWN;
import static com.amhzing.activities.ui.application.Failure.SYSTEM_RETURNED_ERRORS;
import static io.atlassian.fugue.Either.left;
import static io.atlassian.fugue.Either.right;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

public class CircuitBreakingParticipantService implements DefaultParticipantService {

    private static final String GROUP_KEY = "participantsGroupKey";
    private static final String COMMAND_KEY_BY_CRITERIA = "participantsByCriteria";
    private static final String FALLBACK = "fallback";

    private ExternalParticipantService externalParticipantService;

    public CircuitBreakingParticipantService(final ExternalParticipantService externalParticipantService) {
        this.externalParticipantService = notNull(externalParticipantService);
    }

    @Override
    @HystrixCommand(groupKey = GROUP_KEY, commandKey = COMMAND_KEY_BY_CRITERIA, fallbackMethod = FALLBACK)
    public Either<Failure, Participants> participantsByCriteria(final QueryCriteria queryCriteria) {
        notNull(queryCriteria);

        final ParticipantResponse response = externalParticipantService.searchParticipants(searchSpecification(queryCriteria));

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

    private static SearchSpecification searchSpecification(final QueryCriteria queryCriteria) {
        return SearchSpecification.create(queryCriteria.getCountry(),
                                          queryCriteria.getCity(),
                                          queryCriteria.getAddressLine1(),
                                          queryCriteria.getLastName(),
                                          queryCriteria.getParticipantId());
    }
}
