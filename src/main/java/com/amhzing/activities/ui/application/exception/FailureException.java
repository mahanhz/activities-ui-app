package com.amhzing.activities.ui.application.exception;

public class FailureException extends RuntimeException {

    private String errorType;
    private String correlatedErrorId;

    public FailureException(final String message, final String errorType, final String correlatedErrorId) {
        super(message);
        this.errorType = errorType;
        this.correlatedErrorId = correlatedErrorId;
    }

    public FailureException(final String message, final Throwable cause, final String errorType, final String correlatedErrorId) {
        super(message, cause);
        this.errorType = errorType;
        this.correlatedErrorId = correlatedErrorId;
    }

    public FailureException(final Throwable cause, final String errorType, final String correlatedErrorId) {
        super(cause);
        this.errorType = errorType;
        this.correlatedErrorId = correlatedErrorId;
    }

    public FailureException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace, final String errorType, final String correlatedErrorId) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorType = errorType;
        this.correlatedErrorId = correlatedErrorId;
    }

    public String getErrorType() {
        return errorType;
    }

    public String getCorrelatedErrorId() {
        return correlatedErrorId;
    }
}
