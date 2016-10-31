package com.amhzing.activities.ui.web.client.vaadin.page;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.amhzing.activities.ui.helper.JUnitParamHelper.invalidMatching;
import static com.amhzing.activities.ui.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class CountryTest {

    @Test
    @Parameters(method = "countryValues")
    public void country_is_valid(final Class<? extends Exception> exception, final String code, final String name)  {
        try {
            Country.create(code, name);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @SuppressWarnings("unused")
    private Object countryValues() {
        return new Object[][]{
                {valid(), "SE", "Sweden"},
                {valid(), "DK", "Denmark"},
                {invalidMatching(IllegalArgumentException.class), "", ""},
                {invalidMatching(NullPointerException.class), null, null},
                {invalidMatching(NullPointerException.class), null, ""},
                {invalidMatching(IllegalArgumentException.class), "", null},
                {invalidMatching(IllegalArgumentException.class), "", "Sweden"},
                {invalidMatching(NullPointerException.class), null, "Sweden"},
                {invalidMatching(IllegalArgumentException.class), "", ""},
                {invalidMatching(NullPointerException.class), null, null}
        };
    }
}