package com.amhzing.activities.ui.external.participant.domain;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.amhzing.activities.ui.helper.JUnitParamHelper.invalidMatching;
import static com.amhzing.activities.ui.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class AddressTest {

    @Test
    @Parameters(method = "addressValues")
    public void address_is_valid(final Class<? extends Exception> exception,
                                 final String addressLine1,
                                 final String addressLine2,
                                 final String city,
                                 final String postalCode,
                                 final String countryCode,
                                 final String countryName)  {
        try {
            Address.create(addressLine1,
                           addressLine2,
                           city,
                           postalCode,
                           Country.create(countryCode, countryName));
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @SuppressWarnings("unused")
    private Object addressValues() {
        return new Object[][]{
                {valid(), addressLine1(), addressLine2(), city(), postalCode(), countryCode(), countryName()},
                {valid(), addressLine1(), null, city(), null, countryCode(), null},
                {invalidMatching(IllegalArgumentException.class), "", addressLine2(), city(), postalCode(), countryCode(), countryName()},
                {invalidMatching(NullPointerException.class), addressLine1(), addressLine2(), null, postalCode(), countryCode(), countryName()},
                {invalidMatching(IllegalArgumentException.class), addressLine1(), addressLine2(), city(), postalCode(), "", countryName()}
        };
    }

    private String postalCode() {
        return "pCode";
    }

    private String city() {
        return "city";
    }

    private String addressLine2() {
        return "ad2";
    }

    private String addressLine1() {
        return "ad1";
    }

    private String countryCode() {
        return "SE";
    }

    private String countryName() {
        return "Sweden";
    }
}