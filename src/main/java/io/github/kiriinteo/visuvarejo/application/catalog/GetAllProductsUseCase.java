package io.github.kiriinteo.visuvarejo.application.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import io.github.kiriinteo.visuvarejo.core.domain.Product;
import io.github.kiriinteo.visuvarejo.core.port.ProductRepository;
import io.github.kiriinteo.visuvarejo.adapter.web.catalog.dto.ProductResponse;

@Service
@RequiredArgsConstructor
public class GetAllProductsUseCase {

    private final ProductRepository productRepository;

    public List<ProductResponse> execute() {

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getPrice().getValue(),
                        product.getCategoryId(),
                        product.isActive(),
                        product.getCompanyId()
                ))
                .toList();
    }
}
