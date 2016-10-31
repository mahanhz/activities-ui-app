package com.amhzing.activities.ui.helper;

import com.amhzing.activities.ui.external.participant.domain.*;
import com.amhzing.activities.ui.external.participant.response.ErrorResponse;
import com.amhzing.activities.ui.external.participant.response.ParticipantResponse;
import com.google.common.collect.ImmutableList;

public class ExternalParticipantHelper {

    private ExternalParticipantHelper() {
        // To prevent instantiation
    }

    public static ParticipantResponse response(final ErrorResponse errorResponse) {
        return ParticipantResponse.create(ImmutableList.of(participantInfo()), ImmutableList.of(errorResponse));
    }

    public static ParticipantInfo participantInfo() {
        return ParticipantInfo.create("participantId", name(), address(), contactNumber(), email());
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

    public static ErrorResponse noError() {
        return ErrorResponse.create("", "", "");
    }

    public static ErrorResponse error() {
        return ErrorResponse.create("Code1", "Message1", "CorrelationId1");
    }
}
