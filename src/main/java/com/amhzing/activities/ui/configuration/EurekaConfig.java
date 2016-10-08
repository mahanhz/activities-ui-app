package com.amhzing.activities.ui.configuration;

import com.amhzing.activities.ui.annotation.Online;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

@Online
@Configuration
@EnableEurekaClient
public class EurekaConfig {
}
