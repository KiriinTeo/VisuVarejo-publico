package io.github.kiriinteo.visuvarejo.application.catalog;

import io.github.kiriinteo.visuvarejo.core.domain.Money;
import io.github.kiriinteo.visuvarejo.core.domain.Product;
import io.github.kiriinteo.visuvarejo.core.port.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CreateProductUseCase {

    private final ProductRepository productRepository;

    public CreateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(String name, BigDecimal price) {

        Product product = new Product(
                UUID.randomUUID(),
                name,
                new Money(price)
        );

        return productRepository.save(product);
    }
}
