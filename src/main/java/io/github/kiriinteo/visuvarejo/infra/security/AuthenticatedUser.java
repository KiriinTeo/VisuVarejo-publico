package io.github.kiriinteo.visuvarejo.infra.security;

import java.util.UUID;

public class AuthenticatedUser {

    private final String email;
    private final UUID companyId;
    private final String role;

    public AuthenticatedUser(String email, UUID companyId, String role) {
        this.email = email;
        this.companyId = companyId;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public String getRole() {
        return role;
    }
}
