package io.github.kiriinteo.visuvarejo.core.domain;

import java.util.UUID;

public class Company {

    private UUID id;
    private String name;
    private String document; // CNPJ ou identificador

    public Company(UUID id, String name, String document) {
        this.id = id;
        this.name = name;
        this.document = document;
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
}