package com.roloduck.security;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/6/14
 * RoloDuck
 */

public enum Authority {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_AUTHORITY("ROLE_AUTHORITY"),
    ROLE_USER("ROLE_USER"),
    ROLE_ANONYMOUS("ROLE_ANONYMOUS");

    private String userTypeValue;

    private Authority(String type) {
        this.userTypeValue = type;
    }

    public String value() {
        return String.valueOf(ordinal());
    }

    public String getStringValue() {
        return this.userTypeValue;
    }
}
