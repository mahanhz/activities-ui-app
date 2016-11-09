package com.amhzing.activities.ui.web.client.angular.model;

import com.amhzing.activities.ui.web.client.model.AddressModel;
import com.amhzing.activities.ui.web.client.model.NameModel;
import com.amhzing.activities.ui.web.client.model.ParticipantModel;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static com.amhzing.activities.ui.web.client.exception.UIFriendlyException.HTML_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;

public class ParticipantsTest {

    @Test
    public void should_create_participants() {
        final Participants participants = Participants.createErrorFree(ImmutableList.of(participant()));

        assertThat(participants.getParticipants()).containsExactly(participant());
    }

    @Test
    public void should_create_participants_with_error() {
        final Participants participants = Participants.createWithError(HTML_ERROR_MESSAGE);

        assertThat(participants.getParticipants()).isEmpty();
        assertThat(participants.getErrorMessage()).isEqualTo(HTML_ERROR_MESSAGE);
    }

    @Test
    public void should_create_same_participants() {
        final Participants participants = Participants.createErrorFree(ImmutableList.of(participant()));
        final Participants participants2 = Participants.create(ImmutableList.of(participant()), "");

        assertThat(participants).isEqualTo(participants2);
    }

    private ParticipantModel participant() {
        return ParticipantModel.create("pId", name(), address(), contactNumber(), email());
    }

    private static AddressModel address() {
        return AddressModel.create("ad1", "ad2", "city", "pCode", "SE");
    }

    private static NameModel name() {
        return NameModel.create("fname", "mName", "lName", "II");
    }

    private static String contactNumber() {
        return "12345678";
    }

    private static String email() {
        return "test@example.com";
    }
}