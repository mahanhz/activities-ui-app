package com.amhzing.activities.ui.configuration;

import com.amhzing.activities.ui.feature.AppFeatures;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.togglz.core.Feature;
import org.togglz.core.manager.EnumBasedFeatureProvider;
import org.togglz.core.manager.TogglzConfig;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.mem.InMemoryStateRepository;
import org.togglz.core.spi.FeatureProvider;
import org.togglz.core.user.UserProvider;
import org.togglz.spring.security.SpringSecurityUserProvider;

@Configuration
public class FeatureConfig implements TogglzConfig {

    @Bean
    public FeatureProvider featureProvider() {
        return new EnumBasedFeatureProvider(AppFeatures.class);
    }

    @Override
    public Class<? extends Feature> getFeatureClass() {
        return AppFeatures.class;
    }

    // TODO - consider using a property based state repository
    @Override
    public StateRepository getStateRepository() {
        return new InMemoryStateRepository();
    }

    @Override
    public UserProvider getUserProvider() {
        return new SpringSecurityUserProvider("ROLE_ADMIN");
    }
}
