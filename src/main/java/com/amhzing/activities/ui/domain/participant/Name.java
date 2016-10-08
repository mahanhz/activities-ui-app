package com.amhzing.activities.ui.domain.participant;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

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

    public static Name create(final String firstName,
                              final String middleName,
                              final String lastName,
                              final String suffix) {
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
        return "NameModel{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", suffix='" + suffix + '\'' +
                '}';
    }
}
