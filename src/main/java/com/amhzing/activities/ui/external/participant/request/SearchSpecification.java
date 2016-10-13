package com.amhzing.activities.ui.external.participant.request;

import static org.apache.commons.lang3.Validate.notBlank;

public class SearchSpecification {

    private final String country;
    private final String city;
    private final String addressLine1;
    private final String lastName;
    private final String participantId;

    private SearchSpecification(final String country, final String city, final String addressLine1, final String lastName, final String participantId) {
        this.country = notBlank(country);
        this.city = city;
        this.addressLine1 = addressLine1;
        this.lastName = lastName;
        this.participantId = participantId;
    }

    public static SearchSpecification create(final String country, final String city, final String addressLine1, final String lastName, final String participantId) {
        return new SearchSpecification(country, city, addressLine1, lastName, participantId);
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
    public String toString() {
        return "SearchSpecification{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", lastName='" + lastName + '\'' +
                ", participantId='" + participantId + '\'' +
                '}';
    }
}
