package io.github.kiriinteo.visuvarejo.core.domain;

import java.util.UUID;

public class Category {

    private final UUID id;
    private String name;
    private UUID companyId;

    public Category(UUID id, String name, UUID companyId) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome de Categoria não pode ser vazio");
        }

        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UUID getCompanyId() {
        return companyId;
    }
}
