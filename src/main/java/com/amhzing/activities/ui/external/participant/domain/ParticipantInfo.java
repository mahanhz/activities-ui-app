package com.amhzing.activities.ui.external.participant.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@JsonInclude
public class ParticipantInfo {

    private final String participantId;
    private final Name name;
    private final Address address;
    private final ContactNumber contactNumber;
    private final Email email;

    private ParticipantInfo(final String participantId, final Name name, final Address address,
                            final ContactNumber contactNumber, final Email email) {
        this.participantId = notBlank(participantId);
        this.name = notNull(name);
        this.address = notNull(address);
        this.contactNumber = contactNumber;
        this.email = email;
    }

    @JsonCreator
    public static ParticipantInfo create(@JsonProperty("participantId") final String participantId,
                                         @JsonProperty("name") final Name name,
                                         @JsonProperty("address") final Address address,
                                         @JsonProperty("contactNumber") final ContactNumber contactNumber,
                                         @JsonProperty("email") final Email email) {
        return new ParticipantInfo(participantId, name, address, contactNumber, email);
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
        final ParticipantInfo that = (ParticipantInfo) o;
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
