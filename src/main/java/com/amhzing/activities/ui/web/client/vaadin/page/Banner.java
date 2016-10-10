package com.amhzing.activities.ui.web.client.vaadin.page;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

import static org.apache.commons.lang3.Validate.notBlank;

public class Banner extends Window {

    public Banner() {
        setVisible(false);
        setResizable(false);
        setSizeFull();
        setPosition(0, 0);
        setStyleName("banner-translucent");
    }

    public void show(final String htmlMessage) {
        notBlank(htmlMessage);

        final Label content = new Label(htmlMessage, ContentMode.HTML);
        content.setStyleName("banner-message");
        setContent(content);
        setVisible(true);
    }
}
