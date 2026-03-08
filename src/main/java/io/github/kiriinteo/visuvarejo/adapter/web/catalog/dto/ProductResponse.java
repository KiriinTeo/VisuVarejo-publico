package io.github.kiriinteo.visuvarejo.adapter.web.catalog.dto;

import java.math.BigDecimal;
import java.util.UUID;

import io.github.kiriinteo.visuvarejo.core.domain.Product;

public record ProductResponse(
        UUID id,
        String name,
        BigDecimal price,
        UUID categoryId,
        boolean active,
        UUID companyId
) {
   public static ProductResponse fromDomain(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice().getValue(),
                product.getCategoryId(),
                product.isActive(),
                product.getCompanyId()
        );
    }
}
