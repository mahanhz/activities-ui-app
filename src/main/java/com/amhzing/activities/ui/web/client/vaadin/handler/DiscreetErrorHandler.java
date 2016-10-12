package com.amhzing.activities.ui.web.client.vaadin.handler;

import com.fasterxml.uuid.Generators;
import com.vaadin.server.*;
import com.vaadin.ui.AbstractComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class DiscreetErrorHandler extends DefaultErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscreetErrorHandler.class);

    @Override
    public void error(final ErrorEvent event) {
        final Throwable throwable = findRelevantThrowable(event.getThrowable());
        final AbstractComponent component = findAbstractComponent(event);

        final UUID errorId = Generators.timeBasedGenerator().generate();
        LOGGER.error("ErrorId: {} references the following error: ", errorId, throwable);

        if (component != null) {
            component.setComponentError(new SystemError("An error occurred. Please reference Error Id: " + errorId));
        }
    }
}
