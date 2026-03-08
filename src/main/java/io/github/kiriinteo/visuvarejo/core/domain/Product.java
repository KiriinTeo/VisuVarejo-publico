package io.github.kiriinteo.visuvarejo.core.domain;

import java.util.UUID;

public class Product {

    private final UUID id;
    private String name;
    private Money price;
    private boolean active;
    private UUID categoryId;
    private UUID companyId;

    public Product(UUID id, String name, Money price, UUID categoryId, UUID companyId) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome de produto não pode ser vazio");
        }

        if (price == null || !price.isGreaterThanZero()) {
            throw new IllegalArgumentException("Preço do produto deve ser maior que zero");
        }

        if (categoryId == null) {
            throw new IllegalArgumentException("Produto deve pertencer a uma categoria");
        }

        this.id = id;
        this.name = name;
        this.price = price;
        this.active = true;
        this.categoryId = categoryId;
        this.companyId = companyId;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public void updatePrice(Money newPrice) {
        if (newPrice == null || !newPrice.isGreaterThanZero()) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }
        this.price = newPrice;
    }

    public void updateName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        this.name = newName;
    }

    public UUID getCompanyId() {
        return companyId;
    }
}

