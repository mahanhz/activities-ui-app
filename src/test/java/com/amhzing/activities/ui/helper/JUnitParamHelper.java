package com.amhzing.activities.ui.helper;

public class JUnitParamHelper {

    private JUnitParamHelper() {
    }

    public static Class<? extends Exception> valid() {
        return null;
    }

    public static Class<? extends Exception> invalidMatching(final Class<? extends Exception> exception) {
        return exception;
    }
}
