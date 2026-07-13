package io.github.kiriinteo.visuvarejo.application.catalog;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import io.github.kiriinteo.visuvarejo.core.domain.Category;
import io.github.kiriinteo.visuvarejo.core.port.CategoryRepository;
import io.github.kiriinteo.visuvarejo.adapter.web.catalog.dto.CreateCategoryRequest;
import io.github.kiriinteo.visuvarejo.adapter.web.catalog.dto.CategoryResponse;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateCategoryUseCase {

    private final CategoryRepository categoryRepository;

    @CacheEvict(value = "categories", key = "#companyId")
    public CategoryResponse execute(CreateCategoryRequest request, UUID companyId) {
        Category category = new Category(UUID.randomUUID(),request.name(), companyId);

        categoryRepository.save(category);

        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getCompanyId()
        );
    }
}
