package com.amhzing.activities.ui.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

    public static final String ADMIN = "ADMIN";
    public static final String VAADIN_USER = "VAADIN_USER";

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin").password("p").roles(ADMIN)
            .and()
            .withUser("vaadin").password("p").roles(VAADIN_USER);
    }
}
