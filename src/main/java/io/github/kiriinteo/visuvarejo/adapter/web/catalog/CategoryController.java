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
import io.github.kiriinteo.visuvarejo.application.catalog.GetAllCategoriesUseCase;
import io.github.kiriinteo.visuvarejo.application.catalog.GetCategoryByIdUseCase;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetAllCategoriesUseCase getAllCategoriesUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;

    @PostMapping
    public CategoryResponse create(@RequestBody CreateCategoryRequest request) {
        return createCategoryUseCase.execute(request);
    }

    @GetMapping
    public List<CategoryResponse> findAll() {
       return getAllCategoriesUseCase.execute().stream()
            .map(c -> new CategoryResponse(c.getId(), c.getName(), c.getCompanyId()))
            .toList();
                
    }

    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable UUID id) {

        Category category = getCategoryByIdUseCase.execute(id);

        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getCompanyId()
        );
    }

}
