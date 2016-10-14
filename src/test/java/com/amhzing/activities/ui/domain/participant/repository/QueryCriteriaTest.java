package com.amhzing.activities.ui.domain.participant.repository;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.amhzing.activities.ui.util.JUnitParamHelper.invalidMatching;
import static com.amhzing.activities.ui.util.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class QueryCriteriaTest {

    @Test
    @Parameters(method = "queryCriteriaValues")
    public void query_criteria_is_valid(final Class<? extends Exception> exception,
                                        final String country,
                                        final String city,
                                        final String addressLine1,
                                        final String lastName,
                                        final String participantId) {
        try {
            QueryCriteria.create(country, city, addressLine1, lastName, participantId);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    private Object queryCriteriaValues() {
        return new Object[][]{
                { valid(), country(), city(), addressLine1(), lastName(), participantId() },
                { valid(), country(), "", "", "", "" },
                { valid(), country(), null, null, null, null },
                { invalidMatching(IllegalArgumentException.class), "", city(), addressLine1(), lastName(), participantId() },
                { invalidMatching(NullPointerException.class), null, city(), addressLine1(), lastName(), participantId() },
        };
    }

    private String country() {
        return "se";
    }

    private String city() {
        return "city";
    }

    private String addressLine1() {
        return "ad1";
    }

    private String lastName() {
        return "doe";
    }

    private String participantId() {
        return "pId";
    }
}