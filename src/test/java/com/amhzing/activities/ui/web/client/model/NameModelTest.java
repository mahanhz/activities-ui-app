package com.amhzing.activities.ui.web.client.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.amhzing.activities.ui.helper.JUnitParamHelper.invalidMatching;
import static com.amhzing.activities.ui.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class NameModelTest {

    @Test
    @Parameters(method = "nameValues")
    public void name_is_valid(final Class<? extends Exception> exception,
                              final String firstName,
                              final String middleName,
                              final String lastName,
                              final String suffix)  {
        try {
            NameModel.create(firstName, middleName, lastName, suffix);
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(exception);
        }
    }

    private Object nameValues() {
        return new Object[][]{
                {valid(), firstName(), middleName(), lastName(), suffix()},
                {valid(), null, null, lastName(), null},
                {invalidMatching(IllegalArgumentException.class), firstName(), middleName(), "", suffix()},
                {invalidMatching(NullPointerException.class), firstName(), middleName(), null, suffix()}
        };
    }

    @Test
    public void should_create_name() {
        final NameModel nameModel = new NameModel();
        nameModel.setFirstName(firstName());
        nameModel.setMiddleName(middleName());
        nameModel.setLastName(lastName());
        nameModel.setSuffix(suffix());

        assertThat(nameModel).isEqualTo(NameModel.create(firstName(), middleName(), lastName(), suffix()));
    }

    private String firstName() {
        return "firstName";
    }

    private String middleName() {
        return "middleName";
    }

    private String lastName() {
        return "lastName";
    }

    private String suffix() {
        return "suffix";
    }
}