package com.amhzing.activities.ui.web.client.model;

import junitparams.JUnitParamsRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@RunWith(JUnitParamsRunner.class)
public class ParticipantModelTest {

    @Test
    public void should_create_valid_participant() {
        final ParticipantModel participant = ParticipantModel.create("pId", name(), address(), contactNumber(), email());

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

    private AddressModel address() {
        return AddressModel.create("ad1", "ad2", "city", "pCode", "SE");
    }

    private NameModel name() {
        return NameModel.create("fname", "mName", "lName", "II");
    }

    private String contactNumber() {
        return "12345678";
    }

    private String email() {
        return "test@example.com";
    }
}