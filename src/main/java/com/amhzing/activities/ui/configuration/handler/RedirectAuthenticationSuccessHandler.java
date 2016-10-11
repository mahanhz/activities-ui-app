package com.amhzing.activities.ui.configuration.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.amhzing.activities.ui.configuration.AuthenticationConfig.ADMIN;
import static com.amhzing.activities.ui.configuration.AuthenticationConfig.VAADIN_USER;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.replace;

public class RedirectAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String ROLE = "ROLE_";
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {
        final SavedRequest savedRequest = requestCache.getRequest(request, response);
        String targetUrl = "";

        final String roleBasedTargetUrl = url(authentication);

        if ((savedRequest == null) && isBlank(roleBasedTargetUrl)) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        clearAuthenticationAttributes(request);

        targetUrl = useSavedUrl(savedRequest, request) ? savedRequest.getRedirectUrl() : roleBasedTargetUrl;
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    String url(Authentication authentication) {
        final List<String> roles = authentication.getAuthorities()
                                                 .stream()
                                                 .map(GrantedAuthority::getAuthority)
                                                 .collect(toList());

        if (roles.contains(ROLE + VAADIN_USER)) {
            return "/vaadin";
        } else if (roles.contains(ROLE + ADMIN)) {
            return "/togglz-console";
        }

        return null;
    }

    private boolean useSavedUrl(final SavedRequest savedRequest, final HttpServletRequest request) {
        if (savedRequest == null) {
            return false;
        } else if (savedRequest instanceof DefaultSavedRequest) {
            final String strippedSavedRequestURI = replace(((DefaultSavedRequest) savedRequest).getRequestURI(), "/", "");
            final String strippedRequestContextPath = replace(request.getContextPath(), "/", "");

            if (strippedSavedRequestURI.equalsIgnoreCase(strippedRequestContextPath)) {
                return false;
            }
        }

        return true;
    }
}
