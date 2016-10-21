package com.amhzing.activities.ui.configuration;

import com.amhzing.activities.ui.annotation.Offline;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

import static com.amhzing.activities.ui.user.UserStore.*;

@Offline
@Configuration
public class AuthenticationConfigOffline extends GlobalAuthenticationConfigurerAdapter {

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser(USER_ADMIN.getName())
            .password(USER_ADMIN.getPassword())
            .roles(USER_ADMIN.getRolesShortName())
            .and()
            .withUser(USER_VAADIN.getName())
            .password(USER_VAADIN.getPassword())
            .roles(USER_VAADIN.getRolesShortName())
            .and()
            .withUser(USER_COORDINATOR.getName())
            .password(USER_COORDINATOR.getPassword())
            .roles(USER_COORDINATOR.getRolesShortName());
    }
}
