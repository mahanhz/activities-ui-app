package com.amhzing.activities.ui.web.client.model;

public final class ParticipantModelHelper {

    private ParticipantModelHelper() {
        // To prevent instantiation
    }

    public static AddressModel address() {
        return AddressModel.create("ad1", "ad2", "city", "pCode", "SE");
    }

    public static NameModel name() {
        return NameModel.create("fname", "mName", "lName", "II");
    }

    public static String contactNumber() {
        return "12345678";
    }

    public static String email() {
        return "test@example.com";
    }
}
