package com.amhzing.activities.ui.helper;

import com.amhzing.activities.ui.domain.participant.model.*;

public class DomainParticipantHelper {

    private DomainParticipantHelper() {
        // To prevent instantiation
    }

    public static Address address() {
        return Address.create("ad1", "ad2", "city", "pCode", Country.create("SE", ""));
    }

    public static Name name() {
        return Name.create("fname", "mName", "lName", "II");
    }

    public static ContactNumber contactNumber() {
        return ContactNumber.create("12345678");
    }

    public static Email email() {
        return Email.create("test@example.com");
    }
}
