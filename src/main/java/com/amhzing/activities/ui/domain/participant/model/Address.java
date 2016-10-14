package com.amhzing.activities.ui.domain.participant.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class Address {

    private final String addressLine1;
    private final String addressLine2;
    private final String city;
    private final String postalCode;
    private final Country country;

    private Address(final String addressLine1,
                    final String addressLine2,
                    final String city,
                    final String postalCode,
                    final Country country) {
        this.addressLine1 = notBlank(addressLine1);
        this.addressLine2 = addressLine2;
        this.city = notBlank(city);
        this.postalCode = postalCode;
        this.country = notNull(country);
    }

    public static Address create(final String addressLine1,
                                 final String addressLine2,
                                 final String city,
                                 final String postalCode,
                                 final Country country) {
        return new Address(addressLine1, addressLine2, city, postalCode, country);
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Country getCountry() {
        return country;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Address address = (Address) o;
        return Objects.equals(addressLine1, address.addressLine1) &&
                Objects.equals(addressLine2, address.addressLine2) &&
                Objects.equals(city, address.city) &&
                Objects.equals(postalCode, address.postalCode) &&
                Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressLine1, addressLine2, city, postalCode, country);
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country=" + country +
                '}';
    }
}
