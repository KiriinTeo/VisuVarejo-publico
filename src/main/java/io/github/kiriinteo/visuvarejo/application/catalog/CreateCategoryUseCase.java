package io.github.kiriinteo.visuvarejo.application.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import io.github.kiriinteo.visuvarejo.core.domain.Category;
import io.github.kiriinteo.visuvarejo.core.port.CategoryRepository;
import io.github.kiriinteo.visuvarejo.infra.security.CurrentUserProvider;
import io.github.kiriinteo.visuvarejo.adapter.web.catalog.dto.CreateCategoryRequest;
import io.github.kiriinteo.visuvarejo.adapter.web.catalog.dto.CategoryResponse;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final CurrentUserProvider currentUserProvider;

    public CategoryResponse execute(CreateCategoryRequest request) {

        Category category = new Category(UUID.randomUUID(),request.name(), currentUserProvider.getCompanyId());

        categoryRepository.save(category);

        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getCompanyId()
        );
    }
}
