package com.amhzing.activities.ui.web.client.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.amhzing.activities.ui.helper.JUnitParamHelper.invalidMatching;
import static com.amhzing.activities.ui.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class AddressModelTest {

    @Test
    @Parameters(method = "addressValues")
    public void address_is_valid(final Class<? extends Exception> exception,
                                 final String addressLine1,
                                 final String addressLine2,
                                 final String city,
                                 final String postalCode,
                                 final String country)  {
        try {
            AddressModel.create(addressLine1,
                                addressLine2,
                                city,
                                postalCode,
                                country);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @SuppressWarnings("unused")
    private Object addressValues() {
        return new Object[][]{
                { valid(), addressLine1(), addressLine2(), city(), postalCode(), country() },
                { valid(), addressLine1(), null, city(), null, country() },
                { invalidMatching(IllegalArgumentException.class), "", addressLine2(), city(), postalCode(), country() },
                { invalidMatching(NullPointerException.class), addressLine1(), addressLine2(), null, postalCode(), country() },
                { invalidMatching(IllegalArgumentException.class), addressLine1(), addressLine2(), city(), postalCode(), "" }
        };
    }

    @Test
    public void should_create_address() {
        final AddressModel addressModel = new AddressModel();
        addressModel.setAddressLine1(addressLine1());
        addressModel.setAddressLine2(addressLine2());
        addressModel.setCity(city());
        addressModel.setCountry(country());
        addressModel.setPostalCode(postalCode());

        assertThat(addressModel).isEqualTo(AddressModel.create(addressLine1(),
                                                               addressLine2(),
                                                               city(),
                                                               postalCode(),
                                                               country()));
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

    private String country() {
        return "SE";
    }
}