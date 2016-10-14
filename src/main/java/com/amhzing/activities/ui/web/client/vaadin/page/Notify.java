package com.amhzing.activities.ui.web.client.vaadin.page;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

import static com.vaadin.ui.Notification.Type.*;
import static com.vaadin.ui.Notification.Type.ERROR_MESSAGE;
import static org.apache.commons.lang3.Validate.notNull;

public final class Notify {

    private Notify() {

    }

    public static void standard(final String caption,
                                final String description,
                                final Notification.Type type) {
        notNull(caption);
        notNull(description);
        notNull(type);

        final Notification notification = new Notification(caption,
                                                           description,
                                                           type,
                                                           true);
        notification.setPosition(Position.TOP_RIGHT);
        notification.show(Page.getCurrent());
    }

    public static void error(final String caption) {
        notNull(caption);

        standard(caption, "", ERROR_MESSAGE);
    }

    public static void warn(final String caption) {
        notNull(caption);

        standard(caption, "", WARNING_MESSAGE);
    }
}
