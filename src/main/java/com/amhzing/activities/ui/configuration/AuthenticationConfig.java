package com.amhzing.activities.ui.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

import static com.amhzing.activities.ui.UserRole.ADMIN;
import static com.amhzing.activities.ui.UserRole.COORDINATOR;
import static com.amhzing.activities.ui.UserRole.VAADIN_USER;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin").password("p").roles(ADMIN.getRoleShortName())
            .and()
            .withUser("vaadin").password("p").roles(VAADIN_USER.getRoleShortName())
            .and()
            .withUser("coordinator").password("p").roles(COORDINATOR.getRoleShortName(),
                                                         VAADIN_USER.getRoleShortName());
    }
}
