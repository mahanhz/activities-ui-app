package com.amhzing.activities.ui.configuration.handler;

import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class CsrfSecurityRequestHandler implements RequestMatcher {

    private static final Pattern ALLOWED_METHODS = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
    private static final RegexRequestMatcher DISABLE_CSRF_MATCHER = new RegexRequestMatcher("/vaadin.*", null);

    @Override
    public boolean matches(final HttpServletRequest request) {
        if(ALLOWED_METHODS.matcher(request.getMethod()).matches()){
            return false;
        }

        return !DISABLE_CSRF_MATCHER.matches(request);
    }
}
