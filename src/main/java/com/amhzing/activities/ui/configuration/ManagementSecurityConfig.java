package com.amhzing.activities.ui.configuration;

import com.amhzing.activities.ui.configuration.properties.ManagementProperties;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Set;

import static com.amhzing.activities.ui.user.UserRole.ADMIN;

@Configuration
@EnableConfigurationProperties(ManagementProperties.class)
@Order(ManagementServerProperties.ACCESS_OVERRIDE_ORDER)
public class ManagementSecurityConfig extends WebSecurityConfigurerAdapter {

    // TODO - Investigate getting Turbine to work with an authenticated hystrix.stream
    private static final Set<String> OPEN_ENDPOINTS = ImmutableSet.of("/health", "/info", "/hystrix.stream");

    @Autowired
    private ManagementProperties managementProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatcher(request -> request.getServerPort() == managementProperties.getPort())
            .authorizeRequests()
                .requestMatchers(request -> !isOpenEndpoint(request.getRequestURI(), OPEN_ENDPOINTS))
                .hasRole(ADMIN.getRoleShortName())
                .and()
            .httpBasic();
    }

    private boolean isOpenEndpoint(final String uri, final Set<String> openEndpoints) {
        return openEndpoints.stream()
                            .filter(uri::contains)
                            .findFirst()
                            .isPresent();
    }
}
