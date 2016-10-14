package com.amhzing.activities.ui.configuration.handler;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static com.amhzing.activities.ui.configuration.AuthenticationConfig.ADMIN;
import static com.amhzing.activities.ui.configuration.AuthenticationConfig.ROLE_PREFIX;
import static com.amhzing.activities.ui.configuration.AuthenticationConfig.VAADIN_USER;
import static com.amhzing.activities.ui.configuration.handler.RedirectAuthenticationSuccessHandler.TOGGLZ_CONSOLE_URI;
import static com.amhzing.activities.ui.configuration.handler.RedirectAuthenticationSuccessHandler.VAADIN_URI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RedirectAuthenticationSuccessHandlerTest {

    private final RedirectAuthenticationSuccessHandler handler = new RedirectAuthenticationSuccessHandler();

    @Mock
    private Authentication authentication;

    @Test
    public void should_use_role_based_ui_uri() {
        given(authentication.getAuthorities()).willReturn(uiRole());

        assertThat(handler.uri(authentication)).isEqualTo(VAADIN_URI);

    }

    @Test
    public void should_use_role_based_admin_uri() {
        given(authentication.getAuthorities()).willReturn(adminRole());

        assertThat(handler.uri(authentication)).isEqualTo(TOGGLZ_CONSOLE_URI);

    }

    private Collection uiRole() {
        return ImmutableSet.of(new SimpleGrantedAuthority(ROLE_PREFIX + VAADIN_USER));
    }

    private Collection adminRole() {
        return ImmutableSet.of(new SimpleGrantedAuthority(ROLE_PREFIX + ADMIN));
    }
}