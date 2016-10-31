package com.amhzing.activities.ui.domain.participant.model;

import org.junit.Test;

import static com.amhzing.activities.ui.helper.DomainParticipantHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class ParticipantTest {

    @Test
    public void should_create_valid_participant() {
        final Participant participant = Participant.create("pId", name(), address(), contactNumber(), email());

        assertThat(participant.getName().getLastName()).isEqualTo("lName");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_fail_to_create_if_missing_id() {
        Participant.create("", name(), address(), contactNumber(), email());

        fail("Should not have gotten this far");
    }

    @Test(expected = NullPointerException.class)
    public void should_fail_to_create_if_missing_name() {
        Participant.create("pId", null, address(), contactNumber(), email());

        fail("Should not have gotten this far");
    }

    @Test(expected = NullPointerException.class)
    public void should_fail_to_create_if_missing_address() {
        Participant.create("pId", name(), null, contactNumber(), email());

        fail("Should not have gotten this far");
    }
}