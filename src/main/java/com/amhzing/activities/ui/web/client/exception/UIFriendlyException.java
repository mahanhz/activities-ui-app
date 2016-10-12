package com.amhzing.activities.ui.web.client.exception;

public class UIFriendlyException extends RuntimeException {

    public static final String HTML_ERROR_MESSAGE = "Sorry about this!<br/>Unfortunately something went wrong!";

    public UIFriendlyException() {
    }

    public UIFriendlyException(final String message) {
        super(message);
    }

    public UIFriendlyException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UIFriendlyException(final Throwable cause) {
        super(cause);
    }

    public UIFriendlyException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
