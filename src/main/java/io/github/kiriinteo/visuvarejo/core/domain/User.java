package io.github.kiriinteo.visuvarejo.core.domain;

import java.util.UUID;

public class User {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private Company company;
    private String tenantId;

    public User(UUID id,
                String name,
                String email,
                String password,
                Role role,
                Company company,
                String tenantId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.tenantId = tenantId;
        this.company = company;
    }

    public User(String name,
                String email,
                String password,
                Role role,
                Company company,
                String tenantId) {
        this(UUID.randomUUID(), name, email, password, role, company, tenantId);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public Company getCompany() {
        return company;
    }

    public String getTenantId() {
        return tenantId;
    }
    
}