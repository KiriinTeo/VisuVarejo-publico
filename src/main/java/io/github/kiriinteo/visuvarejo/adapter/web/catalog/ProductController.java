package io.github.kiriinteo.visuvarejo.adapter.web.catalog;

import io.github.kiriinteo.visuvarejo.adapter.web.catalog.dto.CreateProductRequest;
import io.github.kiriinteo.visuvarejo.adapter.web.catalog.dto.ProductResponse;
import io.github.kiriinteo.visuvarejo.application.catalog.CreateProductUseCase;
import io.github.kiriinteo.visuvarejo.application.catalog.GetAllProductsUseCase;
import io.github.kiriinteo.visuvarejo.application.catalog.GetProductByIdUseCase;

import io.github.kiriinteo.visuvarejo.core.domain.Product;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;

    public ProductController(CreateProductUseCase createProductUseCase, GetAllProductsUseCase getAllProductsUseCase, GetProductByIdUseCase getProductByIdUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.getAllProductsUseCase = getAllProductsUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
    }

    @PostMapping
    public ProductResponse create(@RequestBody CreateProductRequest request) {

        Product product = createProductUseCase.execute(
                request.name(),
                request.price()
        );

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice().getValue(),
                product.isActive()
        );
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return getAllProductsUseCase.execute();
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable UUID id) {
        return getProductByIdUseCase.execute(id);
    }
    
}
