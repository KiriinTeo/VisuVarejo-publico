package io.github.kiriinteo.visuvarejo.core.domain;

import java.util.UUID;

public class Company {

    private UUID id;
    private String name;
    private String document; // CNPJ ou identificador
    private String tenantId;

    public Company(UUID id, String name, String document) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.tenantId = UUID.randomUUID().toString();
    }

    public Company(String name, String document) {
        this(UUID.randomUUID(), name, document);
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

    public String getTenantId() {
        return tenantId;
    }
}