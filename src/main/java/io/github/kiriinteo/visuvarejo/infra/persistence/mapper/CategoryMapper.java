package io.github.kiriinteo.visuvarejo.infra.persistence.mapper;

import io.github.kiriinteo.visuvarejo.core.domain.Category;
import io.github.kiriinteo.visuvarejo.infra.persistence.entity.CategoryEntity;

public class CategoryMapper {

    public static CategoryEntity toEntity(Category category) {
        return new CategoryEntity(
                category.getId(),
                category.getName(),
                category.getCompanyId()
        );
    }

    public static Category toDomain(CategoryEntity entity) {
        return new Category(
                entity.getId(),
                entity.getName(),
                entity.getCompanyId()
        );
    }
}
