package com.amhzing.activities.ui.web.client.model;

import static org.apache.commons.lang3.Validate.notBlank;

public class Country {

    private String code;
    private String name;

    private Country(final String code, final String name) {
        this.code = notBlank(code);
        this.name = notBlank(name);
    }

    public static Country create(final String code, final String name) {
        return new Country(code, name);
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
