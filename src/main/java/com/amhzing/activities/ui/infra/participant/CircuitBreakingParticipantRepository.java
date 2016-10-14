package com.amhzing.activities.ui.infra.participant;

import com.amhzing.activities.ui.domain.participant.repository.QueryCriteria;
import com.amhzing.activities.ui.external.participant.ExternalParticipantService;
import com.amhzing.activities.ui.external.participant.request.SearchSpecification;
import com.amhzing.activities.ui.external.participant.response.ErrorResponse;
import com.amhzing.activities.ui.external.participant.response.ParticipantResponse;
import com.fasterxml.uuid.Generators;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.atlassian.fugue.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import static com.amhzing.activities.ui.infra.participant.Failure.SYSTEM_IS_DOWN;
import static com.amhzing.activities.ui.infra.participant.Failure.SYSTEM_RETURNED_ERRORS;
import static io.atlassian.fugue.Either.left;
import static io.atlassian.fugue.Either.right;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

public class CircuitBreakingParticipantRepository implements DefaultParticipantRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CircuitBreakingParticipantRepository.class);

    private static final String GROUP_KEY = "participantsGroupKey";
    private static final String COMMAND_KEY_BY_CRITERIA = "participantsByCriteria";
    private static final String FALLBACK = "fallback";

    private ExternalParticipantService externalParticipantService;

    public CircuitBreakingParticipantRepository(final ExternalParticipantService externalParticipantService) {
        this.externalParticipantService = notNull(externalParticipantService);
    }

    @Override
    @HystrixCommand(groupKey = GROUP_KEY, commandKey = COMMAND_KEY_BY_CRITERIA, fallbackMethod = FALLBACK)
    public Either<CorrelatedFailure, Participants> participantsByCriteria(final QueryCriteria queryCriteria) {
        notNull(queryCriteria);

        final ParticipantResponse response = externalParticipantService.searchParticipants(searchSpecification(queryCriteria));

        if (hasErrors(response.getErrors())) {
            final UUID errorId = Generators.timeBasedGenerator().generate();
            LOGGER.error("External system returned errors in its response. Correlating Error Id: {}", errorId);
            return left(CorrelatedFailure.create(SYSTEM_RETURNED_ERRORS, errorId.toString()));
        }

        final Participants participants = response.getParticipants()
                                                  .stream()
                                                  .map(ParticipantFactory::createParticipant)
                                                  .collect(collectingAndThen(toList(), Participants::create));

        return right(participants);
    }

    public Either<CorrelatedFailure, Participants> fallback(final QueryCriteria queryCriteria, final Throwable e) {
        return left(CorrelatedFailure.failureOnly(SYSTEM_IS_DOWN));
    }

    private boolean hasErrors(final List<ErrorResponse> errors) {
        return errors.stream().filter(error -> !error.isEmpty()).findFirst().isPresent();
    }

    private static SearchSpecification searchSpecification(final QueryCriteria queryCriteria) {
        return SearchSpecification.create(queryCriteria.getCountry(),
                                          queryCriteria.getCity(),
                                          queryCriteria.getAddressLine1(),
                                          queryCriteria.getLastName(),
                                          queryCriteria.getParticipantId());
    }
}
