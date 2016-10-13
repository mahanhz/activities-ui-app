package com.amhzing.activities.ui.infra.participant;

import static org.apache.commons.lang3.Validate.notNull;

public class CorrelatedFailure {

    private final Failure failure;
    private final String errorId;

    private CorrelatedFailure(final Failure failure, final String errorId) {
        this.failure = notNull(failure);
        this.errorId = errorId;
    }

    public static CorrelatedFailure create(final Failure failure, final String errorId) {
        return new CorrelatedFailure(failure, errorId);
    }

    public static CorrelatedFailure failureOnly(final Failure failure) {
        return new CorrelatedFailure(failure, "");
    }

    public Failure getFailure() {
        return failure;
    }

    public String getErrorId() {
        return errorId;
    }
}
