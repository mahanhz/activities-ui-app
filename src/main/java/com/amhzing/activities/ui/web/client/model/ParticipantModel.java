package com.amhzing.activities.ui.web.client.model;

import java.io.Serializable;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class ParticipantModel implements Serializable {

    private String participantId;
    private NameModel name;
    private AddressModel address;
    private String contactNumber;
    private String email;

    public ParticipantModel() {
    }

    private ParticipantModel(final String participantId, final NameModel name, final AddressModel address, final String contactNumber, final String email) {
        this.participantId = notBlank(participantId);
        this.name = notNull(name);
        this.address = notNull(address);
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public static ParticipantModel create(final String participantId, final NameModel name, final AddressModel address, final String contactNumber, final String email) {
        return new ParticipantModel(participantId, name, address, contactNumber, email);
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(final String participantId) {
        this.participantId = participantId;
    }

    public NameModel getName() {
        return name;
    }

    public void setName(final NameModel name) {
        this.name = name;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(final AddressModel address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(final String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ParticipantModel that = (ParticipantModel) o;
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
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
