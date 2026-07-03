package io.github.kiriinteo.visuvarejo.adapter.web.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import io.github.kiriinteo.visuvarejo.application.catalog.CreateCategoryUseCase;
import io.github.kiriinteo.visuvarejo.core.domain.Category;
import io.github.kiriinteo.visuvarejo.core.port.CategoryRepository;
import io.github.kiriinteo.visuvarejo.adapter.web.catalog.dto.CreateCategoryRequest;
import io.github.kiriinteo.visuvarejo.adapter.web.catalog.dto.CategoryResponse;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final CategoryRepository categoryRepository;

    @PostMapping
    public CategoryResponse create(@RequestBody CreateCategoryRequest request) {
        return createCategoryUseCase.execute(request);
    }

    @GetMapping
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryResponse(
                        category.getId(),
                        category.getName(),
                        category.getCompanyId()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable UUID id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getCompanyId()
        );
    }

}
