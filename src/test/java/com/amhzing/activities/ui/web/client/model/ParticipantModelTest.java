package com.amhzing.activities.ui.web.client.model;

import org.junit.Test;

import static com.amhzing.activities.ui.web.client.model.ParticipantModelHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class ParticipantModelTest {

    private static final String PARTICIPANT_ID = "pId";

    @Test
    public void should_create_valid_participant() {
        final ParticipantModel participant = ParticipantModel.create(PARTICIPANT_ID, name(), address(), contactNumber(), email());

        assertThat(participant.getName().getLastName()).isEqualTo("lName");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_fail_to_create_if_missing_id() {
        ParticipantModel.create("", name(), address(), contactNumber(), email());

        fail("Should not have gotten this far");
    }

    @Test(expected = NullPointerException.class)
    public void should_fail_to_create_if_missing_name() {
        ParticipantModel.create("pId", null, address(), contactNumber(), email());

        fail("Should not have gotten this far");
    }

    @Test(expected = NullPointerException.class)
    public void should_fail_to_create_if_missing_address() {
        ParticipantModel.create("pId", name(), null, contactNumber(), email());

        fail("Should not have gotten this far");
    }

    @Test
    public void should_match_participant() {
        final ParticipantModel participantModel = new ParticipantModel();
        participantModel.setParticipantId(PARTICIPANT_ID);
        participantModel.setName(name());
        participantModel.setAddress(address());
        participantModel.setContactNumber(contactNumber());
        participantModel.setEmail(email());

        assertThat(participantModel).isEqualTo(ParticipantModel.create(PARTICIPANT_ID, name(), address(), contactNumber(), email()));
    }
}