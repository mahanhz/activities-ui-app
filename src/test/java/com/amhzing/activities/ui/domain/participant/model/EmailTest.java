package com.amhzing.activities.ui.domain.participant.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.amhzing.activities.ui.helper.JUnitParamHelper.invalidMatching;
import static com.amhzing.activities.ui.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class EmailTest {

    @Test
    @Parameters(method = "emailValues")
    public void email_is_valid(final Class<? extends Exception> exception, final String value)  {
        try {
            Email.create(value);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    private Object emailValues() {
        return new Object[][]{
                {valid(), "me@example.com"},
                {invalidMatching(IllegalArgumentException.class), ""},
                {invalidMatching(NullPointerException.class), null},
                {invalidMatching(IllegalArgumentException.class), "me@test"}
        };
    }
}