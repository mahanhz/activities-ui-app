package com.amhzing.activities.ui.external.participant.domain;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.amhzing.activities.ui.util.JUnitParamHelper.invalidMatching;
import static com.amhzing.activities.ui.util.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ContactNumberTest {

    @Test
    @Parameters(method = "contactNumberValues")
    public void contact_number_is_valid(final Class<? extends Exception> exception, final String value)  {
        try {
            ContactNumber.create(value);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    private Object contactNumberValues() {
        return new Object[][]{
                {valid(), "12345678"},
                {invalidMatching(IllegalArgumentException.class), ""},
                {invalidMatching(NullPointerException.class), null}
        };
    }
}