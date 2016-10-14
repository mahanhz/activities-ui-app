package com.amhzing.activities.ui.application.participant;

import com.amhzing.activities.ui.application.exception.FailureException;
import com.amhzing.activities.ui.domain.participant.model.*;
import com.amhzing.activities.ui.domain.participant.repository.QueryCriteria;
import com.amhzing.activities.ui.infra.participant.CorrelatedFailure;
import com.amhzing.activities.ui.infra.participant.DefaultParticipantRepository;
import com.amhzing.activities.ui.infra.participant.Failure;
import com.amhzing.activities.ui.infra.participant.Participants;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static io.atlassian.fugue.Either.left;
import static io.atlassian.fugue.Either.right;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ParticipantServiceTest {

    @Mock
    private DefaultParticipantRepository defaultParticipantRepository;

    private ParticipantService participantService;

    @Before
    public void before() {
        participantService = new ParticipantService(defaultParticipantRepository);
    }

    @Test
    public void should_get_participants() {
        given(defaultParticipantRepository.participantsByCriteria(any())).willReturn(right(participants()));

        assertThat(participantService.getParticipants(queryCriteria())).isEqualTo(ImmutableList.of(participant()));
    }

    @Test(expected = FailureException.class)
    public void should_fail_with_exception() {
        given(defaultParticipantRepository.participantsByCriteria(any())).willReturn(left(failure()));

        participantService.getParticipants(queryCriteria());

        fail("Exception should have been thrown");
    }

    private CorrelatedFailure failure() {
        return CorrelatedFailure.create(Failure.SYSTEM_IS_DOWN, "8901SE");
    }

    private QueryCriteria queryCriteria() {
        return QueryCriteria.create("us", "", "", "", "");
    }

    private Participants participants() {
        return Participants.create(ImmutableList.of(participant()));
    }

    private Participant participant() {
        return Participant.create("pId", name(), address(), contactNumber(), email());
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
}