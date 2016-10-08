package com.amhzing.activities.ui.domain.participant;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class Participant {

    private final String participantId;
    private final Name name;
    private final Address address;
    private final ContactNumber contactNumber;
    private final Email email;

    private Participant(final String participantId, final Name name, final Address address,
                        final ContactNumber contactNumber, final Email email) {
        this.participantId = notBlank(participantId);
        this.name = notNull(name);
        this.address = notNull(address);
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public static Participant create(final String participantId,
                                     final Name name,
                                     final Address address,
                                     final ContactNumber contactNumber,
                                     final Email email) {
        return new Participant(participantId, name, address, contactNumber, email);
    }

    public String getParticipantId() {
        return participantId;
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public ContactNumber getContactNumber() {
        return contactNumber;
    }

    public Email getEmail() {
        return email;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Participant that = (Participant) o;
        return Objects.equals(participantId, that.participantId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(contactNumber, that.contactNumber) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantId, name, address, contactNumber, email);
    }

    @Override
    public String toString() {
        return "ParticipantModel{" +
                "participantId='" + participantId + '\'' +
                ", name=" + name +
                ", address=" + address +
                ", contactNumber=" + contactNumber +
                ", email=" + email +
                '}';
    }
}
