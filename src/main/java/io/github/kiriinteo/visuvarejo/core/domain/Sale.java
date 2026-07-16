package io.github.kiriinteo.visuvarejo.core.domain;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Sale implements Serializable {

    private final UUID id;
    private final LocalDateTime date;
    private final List<SaleItem> items = new ArrayList<>();
    private UUID companyId;

    public Sale(UUID id, UUID companyId) {
        this.id = id;
        this.companyId = companyId;
        this.date = LocalDateTime.now();
    }

    public void addItem(SaleItem item) {
        items.add(item);
    }

    public Money calculateTotal() {
        Money total = new Money(java.math.BigDecimal.ZERO);

        for (SaleItem item : items) {
            total = total.add(item.getTotal());
        }

        return total;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public List<SaleItem> getItems() {
        return List.copyOf(items);
    }

    public Money getTotalAmount() {
        return calculateTotal();
    }

    public UUID getCompanyId() {
        return companyId;
    }
}
