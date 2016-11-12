package com.amhzing.activities.ui.configuration;

import com.amhzing.activities.ui.configuration.handler.CsrfSecurityRequestHandler;
import com.amhzing.activities.ui.configuration.handler.RedirectAuthenticationSuccessHandler;
import com.amhzing.activities.ui.web.client.angular.filter.CsrfHeaderFilter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static com.amhzing.activities.ui.user.UserRole.ANGULAR_USER;
import static com.amhzing.activities.ui.user.UserRole.VAADIN_USER;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/config-message").permitAll()
                .antMatchers("/angular/**").hasRole(ANGULAR_USER.getRoleShortName())
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
                .newSession() // Create completely new session
            .and()
            .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
            .csrf()
                //.disable(); // Disable for vaadin as it conflicts
                .requireCsrfProtectionMatcher(csrfSecurityRequestHandler())
                .csrfTokenRepository(csrfTokenRepository());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**"); // Static resources are ignored
    }

    private RequestMatcher csrfSecurityRequestHandler() {
        return new CsrfSecurityRequestHandler();
    }

    private CsrfTokenRepository csrfTokenRepository() {
        final HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN"); // this is the name angular uses by default.

        return repository;
    }
}
