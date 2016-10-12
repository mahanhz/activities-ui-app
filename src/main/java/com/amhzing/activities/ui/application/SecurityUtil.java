package com.amhzing.activities.ui.application;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class SecurityUtil {

    private SecurityUtil() {
    }

    public static boolean hasRole(String role) {
        return getPrincipal().getAuthorities().contains(new SimpleGrantedAuthority(role));
    }

    public static UserDetails getPrincipal() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
