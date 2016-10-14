package com.amhzing.activities.ui.web.client.adapter;

import com.amhzing.activities.ui.application.exception.FailureException;
import com.amhzing.activities.ui.application.participant.ParticipantService;
import com.amhzing.activities.ui.domain.participant.model.*;
import com.amhzing.activities.ui.web.client.exception.UIFriendlyException;
import com.amhzing.activities.ui.web.client.model.ParticipantModel;
import com.amhzing.activities.ui.web.client.model.SearchSpecification;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.amhzing.activities.ui.web.client.adapter.ParticipantFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ParticipantAdapterTest {

    @Mock
    private ParticipantService participantService;

    private ParticipantAdapter participantAdapter;

    @Before
    public void before() {
        participantAdapter = new ParticipantAdapter(participantService);
    }

    @Test
    public void should_get_participants() {
        given(participantService.getParticipants(any())).willReturn(participants());

        final List<ParticipantModel> result = participantAdapter.participants(searchSpecification());

        assertThat(result).isEqualTo(participantModel());
    }

    @Test(expected = UIFriendlyException.class)
    public void should_fail_getting_participants() {
        given(participantService.getParticipants(any())).willThrow(FailureException.class);

        participantAdapter.participants(searchSpecification());

        fail("Should not have gotten this far");
    }

    @Test(expected = UIFriendlyException.class)
    public void should_fail_getting_participants_catch_all() {
        given(participantService.getParticipants(any())).willThrow(Exception.class);

        participantAdapter.participants(searchSpecification());

        fail("Should not have gotten this far");
    }

    private List<ParticipantModel> participantModel() {
        return adaptParticipant(participants());
    }

    private SearchSpecification searchSpecification() {
        return SearchSpecification.create("us", "", "", "", "");
    }

    private List<Participant> participants() {
        return ImmutableList.of(participant());
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