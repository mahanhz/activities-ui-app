package com.amhzing.activities.ui.external.participant.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class ParticipantInfoTest {

    private static final String PARTICIPANT_ID = "pId";

    @Test
    public void should_create_valid_participant() {
        final ParticipantInfo participant = ParticipantInfo.create(PARTICIPANT_ID, name(), address(), contactNumber(), email());

        assertThat(participant.getName().getLastName()).isEqualTo("lName");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_fail_to_create_if_missing_id() {
        ParticipantInfo.create("", name(), address(), contactNumber(), email());

        fail("Should not have gotten this far");
    }

    @Test(expected = NullPointerException.class)
    public void should_fail_to_create_if_missing_name() {
        ParticipantInfo.create("pId", null, address(), contactNumber(), email());

        fail("Should not have gotten this far");
    }

    @Test(expected = NullPointerException.class)
    public void should_fail_to_create_if_missing_address() {
        ParticipantInfo.create("pId", name(), null, contactNumber(), email());

        fail("Should not have gotten this far");
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