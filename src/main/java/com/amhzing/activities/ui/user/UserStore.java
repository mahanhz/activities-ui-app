package com.amhzing.activities.ui.user;

import java.util.Arrays;

import static com.amhzing.activities.ui.user.UserRole.*;
import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notBlank;

public enum UserStore {
    USER_ADMIN("admin", "p", ADMIN),
    USER_VAADIN("vaadin", "p", VAADIN_USER),
    USER_COORDINATOR("coordinator", "p", COORDINATOR, VAADIN_USER);

    private final String name;
    private final String password;
    private final UserRole[] roles;

    UserStore(final String name, final String password, final UserRole... roles) {
        this.name = notBlank(name);
        this.password = notBlank(password);
        this.roles = noNullElements(roles);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String[] getRolesShortName() {
        return Arrays.stream(roles)
                     .map(UserRole::getRoleShortName)
                     .toArray(String[]::new);
    }

    public String[] getRolesFullName() {
        return Arrays.stream(roles)
                     .map(UserRole::getRoleFullName)
                     .toArray(String[]::new);
    }
}
