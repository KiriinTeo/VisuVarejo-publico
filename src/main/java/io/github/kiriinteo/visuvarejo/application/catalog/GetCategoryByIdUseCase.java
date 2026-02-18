package io.github.kiriinteo.visuvarejo.application.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import io.github.kiriinteo.visuvarejo.core.domain.Category;
import io.github.kiriinteo.visuvarejo.core.port.CategoryRepository;
import io.github.kiriinteo.visuvarejo.core.exception.DomainException;

@Service
@RequiredArgsConstructor
public class GetCategoryByIdUseCase {

    private final CategoryRepository categoryRepository;

    public Category execute(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new DomainException("Categoria não encontrada"));
    }
}
