package com.amhzing.activities.ui.external.participant.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

@JsonInclude
public class Name {

    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String suffix;

    private Name(final String firstName, final String middleName, final String lastName, final String suffix) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = notBlank(lastName);
        this.suffix = suffix;
    }

    @JsonCreator
    public static Name create(@JsonProperty("firstName") final String firstName,
                              @JsonProperty("middleName") final String middleName,
                              @JsonProperty("lastName") final String lastName,
                              @JsonProperty("suffix") final String suffix) {
        return new Name(firstName, middleName, lastName, suffix);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSuffix() {
        return suffix;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Name name = (Name) o;
        return Objects.equals(firstName, name.firstName) &&
                Objects.equals(middleName, name.middleName) &&
                Objects.equals(lastName, name.lastName) &&
                Objects.equals(suffix, name.suffix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName, suffix);
    }

    @Override
    public String toString() {
        return "Name{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", suffix='" + suffix + '\'' +
                '}';
    }
}
