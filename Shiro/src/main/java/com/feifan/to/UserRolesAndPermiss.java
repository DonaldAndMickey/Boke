package com.feifan.to;

import java.io.Serializable;
import java.util.Set;

public class UserRolesAndPermiss implements Serializable {

    private static final long serialVersionUID = -179637072505399136L;

    private UserTo userTo;
    private Set<String> roles;
    private Set<String> perms;

    public UserTo getUserTo() {
        return userTo;
    }

    public void setUserTo(UserTo userTo) {
        this.userTo = userTo;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return perms;
    }

    public void setPermissions(Set<String> perms) {
        this.perms = perms;
    }

    @Override
    public String toString() {
        return "UserRolesAndPermiss [userTo=" + userTo + ", roles=" + roles + ", perms=" + perms + "]";
    }

}
