package com.amhzing.activities.ui.application.participant;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

public class QueryCriteria {

    private final String country;
    private final String city;
    private final String addressLine1;
    private final String lastName;
    private final String participantId;

    private QueryCriteria(final String country, final String city, final String addressLine1, final String lastName, final String participantId) {
        this.country = notBlank(country);
        this.city = city;
        this.addressLine1 = addressLine1;
        this.lastName = lastName;
        this.participantId = participantId;
    }

    public static QueryCriteria create(final String country, final String city, final String addressLine1, final String lastName, final String participantId) {
        return new QueryCriteria(country, city, addressLine1, lastName, participantId);
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getLastName() {
        return lastName;
    }

    public String getParticipantId() {
        return participantId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final QueryCriteria that = (QueryCriteria) o;
        return Objects.equals(country, that.country) &&
                Objects.equals(city, that.city) &&
                Objects.equals(addressLine1, that.addressLine1) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(participantId, that.participantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, addressLine1, lastName, participantId);
    }

    @Override
    public String toString() {
        return "QueryCriteria{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", lastName='" + lastName + '\'' +
                ", participantId='" + participantId + '\'' +
                '}';
    }
}
