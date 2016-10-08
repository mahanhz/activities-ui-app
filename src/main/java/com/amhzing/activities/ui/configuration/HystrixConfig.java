package com.amhzing.activities.ui.configuration;

import com.amhzing.activities.ui.annotation.Online;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Configuration;

@Online
@Configuration
@EnableHystrix
public class HystrixConfig {
}
