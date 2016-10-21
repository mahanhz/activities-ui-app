package com.amhzing.activities.ui.user;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.apache.commons.lang3.Validate.notNull;

public class UserAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    public UserAuthenticationProvider(final UserDetailsService userDetailsService) {
        this.userDetailsService = notNull(userDetailsService);
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        notNull(authentication);

        final String username = authentication.getName();
        final String password = String.valueOf(authentication.getCredentials());

        final UserDetails user = userDetailsService.loadUserByUsername(username);

        if (user == null
                || !user.getUsername().equalsIgnoreCase(username)
                || !password.equals(user.getPassword())) {
            throw new BadCredentialsException("Wrong username and password");
        }

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
