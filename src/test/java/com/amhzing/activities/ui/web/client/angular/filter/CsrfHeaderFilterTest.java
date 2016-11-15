package com.amhzing.activities.ui.web.client.angular.filter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;

import static com.amhzing.activities.ui.web.client.angular.filter.CsrfHeaderFilter.XSRF_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;

public class CsrfHeaderFilterTest {

    private static final String TOKEN = "abc123def456ghi789";

    private CsrfHeaderFilter csrfHeaderFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterChain chain;
    private CsrfToken token;

    @Before
    public void setup() {
        csrfHeaderFilter = new CsrfHeaderFilter();

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        chain = new MockFilterChain();

        token = new DefaultCsrfToken("X-XSRF-TOKEN", XSRF_TOKEN, TOKEN);
        request.setAttribute(CsrfToken.class.getName(), token);
    }

    @Test
    public void should_add_cookie_to_response() throws ServletException, IOException {
        csrfHeaderFilter.doFilter(request, response, chain);

        assertThat(response.getCookies()).isNotEmpty();
        assertThat(response.getCookie(XSRF_TOKEN).getName()).isEqualTo(XSRF_TOKEN);
        assertThat(response.getCookie(XSRF_TOKEN).getValue()).isEqualTo(TOKEN);
    }

    @Test
    public void should_not_add_cookie_to_response() throws ServletException, IOException {
        request.setCookies(new Cookie(XSRF_TOKEN, TOKEN));

        csrfHeaderFilter.doFilter(request, response, chain);

        assertThat(response.getCookies()).isEmpty();
    }
}