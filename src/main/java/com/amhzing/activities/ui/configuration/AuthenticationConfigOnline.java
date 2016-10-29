package com.amhzing.activities.ui.configuration;

import com.amhzing.activities.ui.annotation.Online;
import com.amhzing.activities.ui.user.DefaultUserDetailsService;
import com.amhzing.activities.ui.user.UserAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Online
@Configuration
public class AuthenticationConfigOnline extends GlobalAuthenticationConfigurerAdapter {

    @Bean
    private UserDetailsService userDetailsService() {
        return new DefaultUserDetailsService();
    }

    @Bean
    private AuthenticationProvider authenticationProvider() {
        return new UserAuthenticationProvider(userDetailsService());
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}
