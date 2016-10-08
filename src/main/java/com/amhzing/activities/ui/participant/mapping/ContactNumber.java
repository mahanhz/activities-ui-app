package com.amhzing.activities.ui.participant.mapping;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

public class ContactNumber {

    private final String value;

    private ContactNumber(final String value) {
        this.value = notBlank(value);
    }

    public static ContactNumber create(final String contactNumber) {
        return new ContactNumber(contactNumber);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ContactNumber that = (ContactNumber) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ContactNumber{" +
                "value='" + value + '\'' +
                '}';
    }
}
