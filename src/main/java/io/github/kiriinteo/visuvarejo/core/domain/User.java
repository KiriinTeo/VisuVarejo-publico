package io.github.kiriinteo.visuvarejo.core.domain;

import java.util.UUID;

public class User {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private Company company;

    public User(UUID id,
                String name,
                String email,
                String password,
                Role role,
                Company company) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.company = company;
    }

    public User(String name,
                String email,
                String password,
                Role role,
                Company company) {
        this(UUID.randomUUID(), name, email, password, role, company);
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
    
}