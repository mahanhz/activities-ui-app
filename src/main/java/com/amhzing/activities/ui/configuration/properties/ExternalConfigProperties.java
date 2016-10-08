package com.amhzing.activities.ui.configuration.properties;

import com.amhzing.activities.ui.annotation.Online;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Online
@ConfigurationProperties(prefix="external.participant")
public class ExternalConfigProperties {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}
