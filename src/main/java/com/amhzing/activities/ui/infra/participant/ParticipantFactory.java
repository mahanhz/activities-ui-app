package com.amhzing.activities.ui.infra.participant;

import com.amhzing.activities.ui.domain.participant.model.*;
import com.amhzing.activities.ui.external.participant.domain.ParticipantInfo;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class ParticipantFactory {

    private ParticipantFactory() {
    }

    public static Participant createParticipant(final ParticipantInfo participantInfo) {
        notNull(participantInfo);

        return Participant.create(participantInfo.getParticipantId(),
                                  name(participantInfo),
                                  address(participantInfo),
                                  contactNumber(participantInfo),
                                  email(participantInfo));
    }

    private static Name name(final ParticipantInfo participantInfo) {
        return Name.create(participantInfo.getName().getFirstName(),
                           participantInfo.getName().getMiddleName(),
                           participantInfo.getName().getLastName(),
                           participantInfo.getName().getSuffix());
    }

    private static Address address(final ParticipantInfo participantInfo) {
        return Address.create(participantInfo.getAddress().getAddressLine1(),
                              participantInfo.getAddress().getAddressLine2(),
                              participantInfo.getAddress().getCity(),
                              participantInfo.getAddress().getPostalCode(),
                              Country.create(participantInfo.getAddress().getCountry().getCode(),
                                             participantInfo.getAddress().getCountry().getName()));
    }

    private static ContactNumber contactNumber(final ParticipantInfo participantInfo) {
        if (participantInfo.getContactNumber() != null && isNotBlank(participantInfo.getContactNumber().getValue())) {
            return ContactNumber.create(participantInfo.getContactNumber().getValue());
        }

        return null;
    }

    private static Email email(final ParticipantInfo participantInfo) {
        if (participantInfo.getEmail() != null  && isNotBlank(participantInfo.getEmail().getValue())){
            return Email.create(participantInfo.getEmail().getValue());
        }

        return null;
    }
}
