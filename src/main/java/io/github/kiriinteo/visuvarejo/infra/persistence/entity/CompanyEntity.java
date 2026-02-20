package io.github.kiriinteo.visuvarejo.infra.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "companies")
public class CompanyEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String document;

    protected CompanyEntity() {}

    public CompanyEntity(UUID id, String name, String document) {
        this.id = id;
        this.name = name;
        this.document = document;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }
}