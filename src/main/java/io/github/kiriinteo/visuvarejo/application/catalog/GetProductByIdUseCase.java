package io.github.kiriinteo.visuvarejo.application.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import io.github.kiriinteo.visuvarejo.core.domain.Product;
import io.github.kiriinteo.visuvarejo.core.port.ProductRepository;
import io.github.kiriinteo.visuvarejo.infra.security.CurrentUserProvider;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetProductByIdUseCase {

    private final ProductRepository productRepository;
    private final CurrentUserProvider currentUserProvider;

    public Product execute(UUID id) {
        UUID companyId = currentUserProvider.getCompanyId();

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (!product.getCompanyId().equals(companyId)) {
            throw new RuntimeException("Produto não encontrado");
        }

        return product;
    }
}