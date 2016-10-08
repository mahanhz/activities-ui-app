package com.amhzing.activities.ui.external.participant.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

@JsonInclude
public class Country {

    private final String code;
    private final String name;

    private Country(final String code, final String name) {
        this.code = notBlank(code);
        this.name = name;
    }

    @JsonCreator
    public static Country create(@JsonProperty("code") final String code,
                                 @JsonProperty("name") final String name) {
        return new Country(code, name);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Country country = (Country) o;
        return Objects.equals(code, country.code) &&
                Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
