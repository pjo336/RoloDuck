package com.roloduck.web.converter;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

public class UserConverter {

    private String name;
    private String email;
    private String password;
    private String companyIdentifier;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyIdentifier() {
        return companyIdentifier;
    }

    public void setCompanyIdentifier(String companyIdentifier) {
        this.companyIdentifier = companyIdentifier;
    }
}
