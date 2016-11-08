package com.amhzing.activities.ui.web.client.angular.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.amhzing.activities.ui.helper.JUnitParamHelper.invalidMatching;
import static com.amhzing.activities.ui.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class AccessTest {

    @Test
    @Parameters(method = "values")
    public void validate_acess(final Class<? extends Exception> exception,
                                 final boolean includeFacilitatedActivity,
                                 final boolean includeHostedActivity)  {
        try {
            Access.create(includeFacilitatedActivity,
                          includeHostedActivity);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { valid(), true, false, },
                { valid(), false, true },
                { invalidMatching(IllegalArgumentException.class), "", "" }
        };
    }

    @Test
    public void should_create_access() {
        final Access access = Access.create(true, false);

        assertThat(access.isIncludeFacilitatedActivity()).isTrue();
        assertThat(access.isIncludeHostedActivity()).isFalse();
    }

    @Test
    public void should_be_same_access() {
        final Access access = Access.create(true, false);

        assertThat(access).isEqualTo(anotherAccess());
    }

    private Access anotherAccess() {
        return Access.create(true, false);
    }
}