package io.github.kiriinteo.visuvarejo.infra.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(name = "company_id", nullable = false, insertable = false, updatable = false)
    private UUID companyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    protected UserEntity() {}

    public UserEntity(UUID id,
                      String name,
                      String email,
                      String password,
                      String role,
                      CompanyEntity company) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.company = company;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public CompanyEntity getCompany() { return company; }
    public UUID getCompanyId() { return company != null ? company.getId() : null; }
}