package io.github.kiriinteo.visuvarejo.infra.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID companyId;

    private String name;

    public CategoryEntity() {}

    public CategoryEntity(UUID id, String name, UUID companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getCompanyId() { return companyId; }
    public void setCompanyId(UUID companyId) { this.companyId = companyId; }

    public String getName() { return name; }

}
