package com.amhzing.activities.ui.configuration;

import com.amhzing.activities.ui.configuration.handler.RedirectAuthenticationSuccessHandler;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.amhzing.activities.ui.user.UserRole.VAADIN_USER;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable() // Use Vaadin's CSRF protection
            .authorizeRequests()
                .antMatchers("/config-message").permitAll()
                .antMatchers("/vaadin/**").hasRole(VAADIN_USER.getRoleShortName())
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .successHandler(new RedirectAuthenticationSuccessHandler())
                .permitAll() // Login page is accessible to anybody
                .and()
            .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .permitAll() // Logout success page is accessible to anybody
                .and()
            .sessionManagement()
                .sessionFixation()
                .newSession(); // Create completely new session
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/*", "/js/*"); // Static resources are ignored
    }
}
