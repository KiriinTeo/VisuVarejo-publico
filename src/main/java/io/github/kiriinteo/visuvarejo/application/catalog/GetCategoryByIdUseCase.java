package io.github.kiriinteo.visuvarejo.application.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import io.github.kiriinteo.visuvarejo.core.domain.Category;
import io.github.kiriinteo.visuvarejo.core.port.CategoryRepository;
import io.github.kiriinteo.visuvarejo.core.exception.DomainException;
import io.github.kiriinteo.visuvarejo.infra.security.CurrentUserProvider;

@Service
@RequiredArgsConstructor
public class GetCategoryByIdUseCase {

    private final CategoryRepository categoryRepository;
    private final CurrentUserProvider currentUserProvider;

    public Category execute(UUID id) {
        UUID companyId = currentUserProvider.getCompanyId();

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new DomainException("Categoria não encontrada"));

        if (!category.getCompanyId().equals(companyId)) {
            throw new RuntimeException("Produto não encontrado");
        }

        return category;
    }
}
