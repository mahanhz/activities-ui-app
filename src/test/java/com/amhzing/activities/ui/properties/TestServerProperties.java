package com.amhzing.activities.ui.properties;

import org.springframework.boot.autoconfigure.web.ServerProperties;

public class TestServerProperties extends ServerProperties {

    private String baseUri;

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(final String baseUri) {
        this.baseUri = baseUri;
    }
}
