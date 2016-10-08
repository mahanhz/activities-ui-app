package com.amhzing.activities.ui.configuration;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;

import static javax.cache.expiry.CreatedExpiryPolicy.factoryOf;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cacheManager -> {
            cacheManager.createCache("activitiesUICache",
                                     new MutableConfiguration<>().setExpiryPolicyFactory(factoryOf(Duration.ONE_HOUR))
                                                                 .setStoreByValue(false));
        };
    }
}
