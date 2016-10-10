package com.amhzing.activities.ui.configuration;

import com.amhzing.activities.ui.configuration.properties.ManagementProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableConfigurationProperties(ManagementProperties.class)
@Order(ManagementServerProperties.ACCESS_OVERRIDE_ORDER)
public class ManagementSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String ADMIN = "ADMIN";

    @Autowired
    private ManagementProperties managementProperties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin").password("p").roles(ADMIN);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatcher(request -> request.getServerPort() == managementProperties.getPort())
            .authorizeRequests()
                .anyRequest().hasRole(ADMIN)
                .and()
            .httpBasic();
    }
}
