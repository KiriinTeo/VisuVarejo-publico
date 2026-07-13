package io.github.kiriinteo.visuvarejo.application.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.UUID;

import io.github.kiriinteo.visuvarejo.core.domain.Category;
import io.github.kiriinteo.visuvarejo.core.port.CategoryRepository;
import io.github.kiriinteo.visuvarejo.core.exception.DomainException;

@Service
@RequiredArgsConstructor
public class GetCategoryByIdUseCase {

    private final CategoryRepository categoryRepository;

    @Cacheable(value = "category", key = "#id")
    public Category execute(UUID id, UUID companyId) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new DomainException("Categoria não encontrada"));

        if (!category.getCompanyId().equals(companyId)) {
            throw new RuntimeException("Categoria não encontrada");
        }

        return category;
    }
}
