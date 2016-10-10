package com.amhzing.activities.ui.configuration.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.amhzing.activities.ui.configuration.AppSecurityConfig.VAADIN_USER;
import static java.util.stream.Collectors.toList;

public class RedirectAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final String ROLE = "ROLE_";

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {
        // request.getRequestDispatcher(determineForwardUrl(authentication)).forward(request, response);
        final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, url(authentication));
    }

    String url(Authentication authentication) {
        final List<String> roles = authentication.getAuthorities()
                                                 .stream()
                                                 .map(GrantedAuthority::getAuthority)
                                                 .collect(toList());

        if (roles.contains(ROLE + VAADIN_USER)) {
            return "/vaadin";
        } else {
            throw new IllegalStateException();
        }
    }
}
