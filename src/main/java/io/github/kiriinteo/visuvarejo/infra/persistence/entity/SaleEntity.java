package io.github.kiriinteo.visuvarejo.infra.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sales")
public class SaleEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID companyId;

    private LocalDateTime date;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleItemEntity> items = new ArrayList<>();

    protected SaleEntity() {}

    public SaleEntity(UUID id, LocalDateTime date, UUID companyId) {
        this.id = id;
        this.date = date;
        this.companyId = companyId;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getCompanyId() { return companyId; }
    public void setCompanyId(UUID companyId) { this.companyId = companyId; }

    public LocalDateTime getDate() { return date; }
    public List<SaleItemEntity> getItems() { return items; }

}
