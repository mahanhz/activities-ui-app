package com.amhzing.activities.ui.configuration.handler;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class CsrfSecurityRequestHandlerTest {

    private CsrfSecurityRequestHandler csrfSecurityRequestHandler;

    @Before
    public void setUP() {
        csrfSecurityRequestHandler = new CsrfSecurityRequestHandler();
    }

    @Test
    public void should_disable_csrf_for_vaadin_requests() throws Exception {

        final MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setMethod("POST");
        mockRequest.setServletPath("/vaadin");

        assertThat(csrfSecurityRequestHandler.matches(mockRequest)).isFalse();
    }

    @Test
    public void should_disable_csrf_for_get_requests() throws Exception {

        final MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setMethod("GET");
        mockRequest.setServletPath("/something");

        assertThat(csrfSecurityRequestHandler.matches(mockRequest)).isFalse();
    }

    @Test
    public void should_enable_csrf_for_post_requests() throws Exception {

        final MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setMethod("POST");
        mockRequest.setServletPath("/something");

        assertThat(csrfSecurityRequestHandler.matches(mockRequest)).isTrue();
    }
}