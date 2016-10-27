package com.amhzing.activities.ui.user;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class DefaultUserDetailsServiceTest {

    private DefaultUserDetailsService defaultUserDetailsService;

    @Before
    public void setUp() {
        defaultUserDetailsService = new DefaultUserDetailsService();
    }

    @Test
    public void should_return_user_details() {
        final UserDetails userDetails = defaultUserDetailsService.loadUserByUsername("admin");

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("admin");

        final SimpleGrantedAuthority[] authorities = userDetails.getAuthorities()
                                                                .stream()
                                                                .toArray(SimpleGrantedAuthority[]::new);
        final SimpleGrantedAuthority[] expectedAuthorities = defaultUserDetailsService.authorities(UserStore.USER_ADMIN)
                                                                                      .stream()
                                                                                      .toArray(SimpleGrantedAuthority[]::new);

        assertThat(authorities).containsExactlyInAnyOrder(expectedAuthorities);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void should_not_find_user() {
        defaultUserDetailsService.loadUserByUsername("hacker");

        fail("The user should not have been found");
    }
}