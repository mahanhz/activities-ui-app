package com.amhzing.activities.ui.infra.participant;

import com.amhzing.activities.ui.domain.participant.repository.QueryCriteria;
import com.amhzing.activities.ui.external.participant.ExternalParticipantService;
import com.amhzing.activities.ui.external.participant.response.ParticipantResponse;
import io.atlassian.fugue.Either;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.amhzing.activities.ui.helper.ExternalParticipantHelper.*;
import static com.amhzing.activities.ui.infra.participant.Failure.SYSTEM_RETURNED_ERRORS;
import static io.atlassian.fugue.Either.right;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class CircuitBreakingParticipantRepositoryTest {

    @Mock
    private ExternalParticipantService externalParticipantService;

    private CircuitBreakingParticipantRepository repository;

    @Before
    public void before() {
        repository = new CircuitBreakingParticipantRepository(externalParticipantService);
    }

    @Test
    public void should_get_participants() {
        final ParticipantResponse response = response(noError());

        given(externalParticipantService.searchParticipants(any())).willReturn(response);

        final Either<CorrelatedFailure, Participants> result = repository.participantsByCriteria(queryCriteria());

        assertThat(result.isRight()).isEqualTo(true);
        assertThat(result).isEqualTo(right(participants(response)));
    }

    @Test
    public void should_fail_with_exception() {
        final ParticipantResponse response = response(error());

        given(externalParticipantService.searchParticipants(any())).willReturn(response);

        final Either<CorrelatedFailure, Participants> result = repository.participantsByCriteria(queryCriteria());

        assertThat(result.isLeft()).isEqualTo(true);
        assertThat(result.left().get().getFailure()).isEqualTo(SYSTEM_RETURNED_ERRORS);
    }

    private Participants participants(final ParticipantResponse response) {
        return response.getParticipants()
                       .stream()
                       .map(ParticipantFactory::createParticipant)
                       .collect(collectingAndThen(toList(), Participants::create));
    }

    private QueryCriteria queryCriteria() {
        return QueryCriteria.create("us", "", "", "", "");
    }
}