package io.github.kiriinteo.visuvarejo.application.catalog;

import io.github.kiriinteo.visuvarejo.core.domain.Money;
import io.github.kiriinteo.visuvarejo.core.domain.Product;
import io.github.kiriinteo.visuvarejo.core.port.ProductRepository;
import io.github.kiriinteo.visuvarejo.infra.security.CurrentUserProvider;
import io.github.kiriinteo.visuvarejo.core.port.CategoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CreateProductUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CurrentUserProvider currentUserProvider;

    public CreateProductUseCase(ProductRepository productRepository, CategoryRepository categoryRepository, CurrentUserProvider currentUserProvider) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.currentUserProvider = currentUserProvider;
    }

    public Product execute(String name, BigDecimal price, UUID categoryId) {

        if (!categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException("Categoria com ID " + categoryId + " não existe");
        }

        Product product = new Product(
                UUID.randomUUID(),
                name,
                new Money(price),
                categoryId,
                UUID.fromString(currentUserProvider.getCompanyId().toString())
        );

        return productRepository.save(product);
    }
}
