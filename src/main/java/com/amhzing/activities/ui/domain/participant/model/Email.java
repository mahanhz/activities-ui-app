package com.amhzing.activities.ui.domain.participant.model;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

public class Email {

    static final int MAX_LENGTH = 50;

    private final String value;

    private Email(final String value) {
        isValid(value);

        this.value = trim(value);
    }

    public static Email create(final String email) {
        return new Email(email);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Email{" +
                "value='" + value + '\'' +
                '}';
    }

    private boolean isValid(final String value) {
        notBlank(value);
        isTrue(value.length() <= MAX_LENGTH);
        isTrue(EmailValidator.getInstance().isValid(value));

        return true;
    }
}
