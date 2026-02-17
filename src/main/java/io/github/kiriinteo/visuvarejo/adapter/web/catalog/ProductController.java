package io.github.kiriinteo.visuvarejo.adapter.web.catalog;

import io.github.kiriinteo.visuvarejo.adapter.web.catalog.dto.CreateProductRequest;
import io.github.kiriinteo.visuvarejo.adapter.web.catalog.dto.ProductResponse;
import io.github.kiriinteo.visuvarejo.application.catalog.CreateProductUseCase;
import io.github.kiriinteo.visuvarejo.core.domain.Product;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;

    public ProductController(CreateProductUseCase createProductUseCase) {
        this.createProductUseCase = createProductUseCase;
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
}
