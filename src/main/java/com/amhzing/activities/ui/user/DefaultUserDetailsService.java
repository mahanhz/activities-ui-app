package com.amhzing.activities.ui.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notBlank;

public class DefaultUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        notBlank(username);

        return Stream.of(UserStore.values())
                     .filter(userStore -> userStore.getName().equalsIgnoreCase(username))
                     .reduce((user1, user2) -> {
                         throw new IllegalStateException("Duplicate users found for: " + username);
                     })
                     .map(userStore -> new User(userStore.getName(), userStore.getPassword(), authorities(userStore)))
                     .orElseThrow(() -> new UsernameNotFoundException("No user found for username: " + username));
    }

    Collection<GrantedAuthority> authorities(final UserStore userStore) {
        return Arrays.stream(userStore.getRolesFullName())
                     .map(SimpleGrantedAuthority::new)
                     .collect(toList());
    }
}
