package com.amhzing.activities.ui.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ExternalConfigOnline.class, ExternalConfigOffline.class})
public class ExternalConfig {

}
