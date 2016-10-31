package com.amhzing.activities.ui.configuration.handler;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

import static com.amhzing.activities.ui.user.UserRole.ADMIN;
import static com.amhzing.activities.ui.user.UserRole.VAADIN_USER;
import static com.amhzing.activities.ui.configuration.handler.RedirectAuthenticationSuccessHandler.TOGGLZ_CONSOLE_URI;
import static com.amhzing.activities.ui.configuration.handler.RedirectAuthenticationSuccessHandler.VAADIN_URI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RedirectAuthenticationSuccessHandlerTest {

    private final RedirectAuthenticationSuccessHandler handler = new RedirectAuthenticationSuccessHandler();

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private Authentication authentication;

    @Test
    public void should_use_role_based_ui_uri() throws Exception {
        given(authentication.getAuthorities()).willReturn(uiRole());

        handler.onAuthenticationSuccess(request, response, authentication);

        assertThat(handler.uri(authentication)).isEqualTo(VAADIN_URI);
    }

    @Test
    public void should_use_role_based_admin_uri() throws Exception {
        given(authentication.getAuthorities()).willReturn(adminRole());

        handler.onAuthenticationSuccess(request, response, authentication);

        assertThat(handler.uri(authentication)).isEqualTo(TOGGLZ_CONSOLE_URI);
    }

    private Collection uiRole() {
        return ImmutableSet.of(new SimpleGrantedAuthority(VAADIN_USER.getRoleFullName()));
    }

    private Collection adminRole() {
        return ImmutableSet.of(new SimpleGrantedAuthority(ADMIN.getRoleFullName()));
    }
}