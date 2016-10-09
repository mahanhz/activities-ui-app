package com.amhzing.activities.ui.configuration;

import com.amhzing.activities.ui.configuration.handler.RedirectAuthenticationSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin").password("password").roles("ADMIN")
            .and()
            .withUser("vaadin").password("password").roles("VAADIN_USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // Use Vaadin's CSRF protection
            .authorizeRequests().anyRequest().authenticated() // User must be authenticated to access any part of the application
            .and()
            .formLogin().loginPage("/login").successHandler(new RedirectAuthenticationSuccessHandler()).permitAll() // Login page is accessible to anybody
            .and()
            .logout().logoutUrl("/logout").invalidateHttpSession(true).permitAll() // Logout success page is accessible to anybody
            .and()
            .sessionManagement().sessionFixation().newSession(); // Create completely new session
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/*", "/js/*", "/less/*"); // Static resources are ignored
    }
}
