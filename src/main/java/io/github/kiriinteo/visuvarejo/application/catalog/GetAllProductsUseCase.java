package io.github.kiriinteo.visuvarejo.application.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import io.github.kiriinteo.visuvarejo.core.domain.Product;
import io.github.kiriinteo.visuvarejo.core.port.ProductRepository;
import io.github.kiriinteo.visuvarejo.infra.security.CurrentUserProvider;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetAllProductsUseCase {

    private final ProductRepository productRepository;
    private final CurrentUserProvider currentUserProvider;

    public List<Product> execute() {
        UUID companyId = currentUserProvider.getCompanyId();

        return productRepository.findByCompanyId(companyId);
    }
}