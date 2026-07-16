package io.github.kiriinteo.visuvarejo.core.domain;

import java.util.UUID;
import java.io.Serializable;

public class SaleItem implements Serializable {

    private final UUID productId;
    private final int quantity;
    private Money unitPrice;
    private String name;
    private UUID companyId;

    public SaleItem(UUID productId, int quantity, Money unitPrice, String name, UUID companyId) {
        if (productId == null) {
            throw new IllegalArgumentException("Produto ID não pode ser nulo");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser nulo ou vazio");
        }

        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.name = name;
        this.companyId = companyId;
    }

    public Money getTotal() {
        return unitPrice.multiply(quantity);
    }

    public UUID getProductId() {
        return productId;
    }

    public Money getUnitPrice() { 
        return unitPrice; 
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public Money getSubtotal() {
        return unitPrice.multiply(quantity);
    }

    public UUID getCompanyId() {
        return companyId;
    }
}
