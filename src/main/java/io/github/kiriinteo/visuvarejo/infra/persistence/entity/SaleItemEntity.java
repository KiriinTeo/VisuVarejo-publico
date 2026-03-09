package io.github.kiriinteo.visuvarejo.infra.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "sale_items")
public class SaleItemEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private SaleEntity sale;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private UUID companyId;

    private int quantity;
    private BigDecimal unitPrice;

    @Column(name = "name", nullable = false)
    private String name;

    protected SaleItemEntity() {}

    public SaleItemEntity(SaleEntity sale, UUID productId, int quantity, BigDecimal unitPrice, String name, UUID companyId) {
        this.sale = sale;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.name = name;
        this.companyId = companyId;
    }

    public UUID getId() { return id; }
    public SaleEntity getSale() { return sale; }
    public UUID getProductId() { return productId; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public UUID getCompanyId() { return companyId; }
    
}
