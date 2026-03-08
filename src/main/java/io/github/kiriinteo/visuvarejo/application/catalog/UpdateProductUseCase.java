package io.github.kiriinteo.visuvarejo.application.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import io.github.kiriinteo.visuvarejo.core.domain.Product;
import io.github.kiriinteo.visuvarejo.core.domain.Money;
import io.github.kiriinteo.visuvarejo.core.port.ProductRepository;
import io.github.kiriinteo.visuvarejo.core.exception.DomainException;
import io.github.kiriinteo.visuvarejo.adapter.web.catalog.dto.ProductResponse;
import io.github.kiriinteo.visuvarejo.adapter.web.catalog.dto.UpdateProductRequest;

@Service
@RequiredArgsConstructor
public class UpdateProductUseCase {

    private final ProductRepository productRepository;

    public ProductResponse execute(UUID id, UpdateProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new DomainException("Produto não encontrado"));

        if (request.name() != null) {
            product.updateName(request.name());
        }

        if (request.price() != null) {
            product.updatePrice(new Money(request.price()));
        }

        if (request.active() != null) {
            if (request.active()) {
                product.activate();
            } else {
                product.deactivate();
            }
        }

        productRepository.save(product);

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice().getValue(),
                product.getCategoryId(),
                product.isActive(),
                product.getCompanyId()
        );
    }
}
