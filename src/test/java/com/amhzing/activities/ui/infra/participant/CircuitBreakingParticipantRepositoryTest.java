package com.amhzing.activities.ui.infra.participant;

import com.amhzing.activities.ui.domain.participant.repository.QueryCriteria;
import com.amhzing.activities.ui.external.participant.ExternalParticipantService;
import com.amhzing.activities.ui.external.participant.domain.*;
import com.amhzing.activities.ui.external.participant.response.ErrorResponse;
import com.amhzing.activities.ui.external.participant.response.ParticipantResponse;
import com.google.common.collect.ImmutableList;
import io.atlassian.fugue.Either;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
        given(externalParticipantService.searchParticipants(any())).willReturn(response());

        final Either<CorrelatedFailure, Participants> response = repository.participantsByCriteria(queryCriteria());

        assertThat(response.isRight()).isEqualTo(true);
        assertThat(response).isEqualTo(right(participants()));
    }

    private Participants participants() {
        return response().getParticipants()
                         .stream()
                         .map(ParticipantFactory::createParticipant)
                         .collect(collectingAndThen(toList(), Participants::create));
    }

    private ParticipantResponse response() {
        return ParticipantResponse.create(ImmutableList.of(participantInfo()), ImmutableList.of(errorResponse()));
    }

    private ParticipantInfo participantInfo() {
        return ParticipantInfo.create("participantId", name(), address(), contactNumber(), email());
    }

    private Address address() {
        return Address.create("ad1", "ad2", "city", "pCode", Country.create("SE", ""));
    }

    private Name name() {
        return Name.create("fname", "mName", "lName", "II");
    }

    private ContactNumber contactNumber() {
        return ContactNumber.create("12345678");
    }

    private Email email() {
        return Email.create("test@example.com");
    }

    private ErrorResponse errorResponse() {
        return ErrorResponse.create("", "", "");
    }

    private QueryCriteria queryCriteria() {
        return QueryCriteria.create("us", "", "", "", "");
    }
}