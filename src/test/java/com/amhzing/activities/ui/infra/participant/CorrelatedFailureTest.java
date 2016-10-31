package com.amhzing.activities.ui.infra.participant;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.amhzing.activities.ui.helper.JUnitParamHelper.invalidMatching;
import static com.amhzing.activities.ui.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class CorrelatedFailureTest {

    @Test
    @Parameters(method = "values")
    public void validate_values(final Class<? extends Exception> exception,
                                final String failure,
                                final String errorId) {
        try {
            CorrelatedFailure.create(Failure.valueOf(failure), errorId);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {valid(), "SYSTEM_IS_DOWN", "ERR_101"},
                {valid(), "SYSTEM_RETURNED_ERRORS", null},
                {invalidMatching(IllegalArgumentException.class), "NONSENSE", null},
                {invalidMatching(NullPointerException.class), null, null}
        };
    }
}