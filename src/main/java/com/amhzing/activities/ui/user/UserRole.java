package com.amhzing.activities.ui.user;

import static org.apache.commons.lang3.Validate.notBlank;

public enum UserRole {

    ADMIN("ROLE_ADMIN", "ADMIN"),
    VAADIN_USER("ROLE_VAADIN_USER", "VAADIN_USER"),
    ANGULAR_USER("ROLE_ANGULAR_USER", "ANGULAR_USER"),
    COORDINATOR("ROLE_COORDINATOR", "COORDINATOR");

    private final String roleFullName;
    private final String roleShortName;

    UserRole(final String roleFullName, final String roleShortName) {
        this.roleFullName = notBlank(roleFullName);
        this.roleShortName = notBlank(roleShortName);
    }

    public String getRoleFullName() {
        return roleFullName;
    }

    public String getRoleShortName() {
        return roleShortName;
    }
}
