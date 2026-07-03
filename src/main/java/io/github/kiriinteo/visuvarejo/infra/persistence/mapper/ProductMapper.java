package io.github.kiriinteo.visuvarejo.infra.persistence.mapper;

import java.math.BigDecimal;

import io.github.kiriinteo.visuvarejo.core.domain.Money;
import io.github.kiriinteo.visuvarejo.core.domain.Product;
import io.github.kiriinteo.visuvarejo.infra.persistence.entity.CategoryEntity;
import io.github.kiriinteo.visuvarejo.infra.persistence.entity.ProductEntity;

public class ProductMapper {

    public static ProductEntity toEntity(Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setPrice(product.getPrice().getValue().doubleValue());
        entity.setActive(product.isActive());
        entity.setCompanyId(product.getCompanyId());

        CategoryEntity category = new CategoryEntity();
        category.setId(product.getCategoryId());

        entity.setCategory(category);

        return entity;
    }

    public static Product toDomain(ProductEntity entity) {
        return new Product(
            entity.getId(),
            entity.getName(),
            new Money(BigDecimal.valueOf(entity.getPrice())),
            entity.getCategory().getId(),
            entity.getCompanyId()
        );
    }
}
