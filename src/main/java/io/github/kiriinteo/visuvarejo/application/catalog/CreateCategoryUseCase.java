package io.github.kiriinteo.visuvarejo.application.catalog;

import io.github.kiriinteo.visuvarejo.core.domain.Category;
import io.github.kiriinteo.visuvarejo.core.port.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateCategoryUseCase {

    private final CategoryRepository categoryRepository;

    public CreateCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category execute(String name) {

        Category category = new Category(
                UUID.randomUUID(),
                name
        );

        return categoryRepository.save(category);
    }
}
