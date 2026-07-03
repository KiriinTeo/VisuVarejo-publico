package io.github.kiriinteo.visuvarejo.infra.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    private UUID id;

    private String name;

    private double price;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column(nullable = false)
    private UUID companyId;

    public ProductEntity() {}

    public ProductEntity(UUID id, String name, double price, boolean active, CategoryEntity category, UUID companyId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.active = active;
        this.category = category;
        this.companyId = companyId;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public CategoryEntity getCategory() { return category; }
    public void setCategory(CategoryEntity category) { this.category = category; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public UUID getCompanyId() { return companyId; }
    public void setCompanyId(UUID companyId) { this.companyId = companyId; }

}
