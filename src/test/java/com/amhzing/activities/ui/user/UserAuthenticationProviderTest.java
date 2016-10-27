package com.amhzing.activities.ui.user;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class UserAuthenticationProviderTest {

    private UserDetailsService userDetailsService;
    private UserAuthenticationProvider userAuthenticationProvider;

    @Before
    public void setUp() {
        userDetailsService = new DefaultUserDetailsService();
        userAuthenticationProvider = new UserAuthenticationProvider(userDetailsService);
    }

    @Test
    public void should_authenticate_successfully() {
        final Authentication authentication = userAuthenticationProvider.authenticate(authenticationToken("p"));

        assertThat(authentication).isNotNull();
        assertThat(authentication.isAuthenticated()).isTrue();
    }

    @Test(expected = BadCredentialsException.class)
    public void should_fail_authentication() {
        userAuthenticationProvider.authenticate(authenticationToken("hacker"));

        fail("Authentication should have failed");
    }

    private TestingAuthenticationToken authenticationToken(final String credentials) {
        return new TestingAuthenticationToken("admin", credentials);
    }
}