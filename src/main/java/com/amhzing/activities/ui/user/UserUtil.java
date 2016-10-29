package com.amhzing.activities.ui.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.apache.commons.lang3.Validate.notBlank;

public final class UserUtil {

    private UserUtil() {
    }

    public static boolean hasRole(final String role) {
        notBlank(role);
        return getPrincipal().getAuthorities().contains(new SimpleGrantedAuthority(role));
    }

    public static UserDetails getPrincipal() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
