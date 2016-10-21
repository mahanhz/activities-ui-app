package com.amhzing.activities.ui.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class UserUtil {

    private UserUtil() {
    }

    public static boolean hasRole(String role) {
        return getPrincipal().getAuthorities().contains(new SimpleGrantedAuthority(role));
    }

    public static UserDetails getPrincipal() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
