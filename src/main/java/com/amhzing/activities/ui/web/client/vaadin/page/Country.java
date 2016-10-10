package com.amhzing.activities.ui.web.client.vaadin.page;

public class Country {

    private String code;
    private String name;

    public Country() {
    }

    private Country(final String code, final String name) {
        this.code = code;
        this.name = name;
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
