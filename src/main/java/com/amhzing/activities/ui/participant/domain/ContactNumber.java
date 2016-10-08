package com.amhzing.activities.ui.participant.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

@JsonInclude
public class ContactNumber {

    private final String value;

    private ContactNumber(final String value) {
        this.value = notBlank(value);
    }

    @JsonCreator
    public static ContactNumber create(@JsonProperty("primaryNumber") final String contactNumber) {
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
