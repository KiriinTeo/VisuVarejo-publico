package io.github.kiriinteo.visuvarejo.application.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import io.github.kiriinteo.visuvarejo.core.domain.Product;
import io.github.kiriinteo.visuvarejo.core.port.ProductRepository;
import io.github.kiriinteo.visuvarejo.core.exception.DomainException;
import io.github.kiriinteo.visuvarejo.adapter.web.catalog.dto.ProductResponse;

@Service
@RequiredArgsConstructor
public class GetProductByIdUseCase {

    private final ProductRepository productRepository;

    public ProductResponse execute(UUID id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new DomainException("Produto não encontrado"));

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
