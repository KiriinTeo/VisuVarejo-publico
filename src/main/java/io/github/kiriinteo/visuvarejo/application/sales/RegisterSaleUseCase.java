package io.github.kiriinteo.visuvarejo.application.sales;

import io.github.kiriinteo.visuvarejo.core.domain.*;
import io.github.kiriinteo.visuvarejo.core.port.ProductRepository;
import io.github.kiriinteo.visuvarejo.core.port.SaleRepository;
import io.github.kiriinteo.visuvarejo.infra.security.CurrentUserProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterSaleUseCase {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final CurrentUserProvider currentUserProvider;

    public Sale execute(List<SaleItemRequest> itemsRequest) {

        Sale sale = new Sale(UUID.randomUUID(), currentUserProvider.getCompanyId());

        for (SaleItemRequest itemRequest : itemsRequest) {

            Product product = productRepository
                    .findById(itemRequest.productId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            SaleItem item = new SaleItem(product.getId(), itemRequest.quantity(), product.getPrice(), product.getName(), currentUserProvider.getCompanyId());
            sale.addItem(item);
        }

        return saleRepository.save(sale);
    }

    public record SaleItemRequest(UUID productId, int quantity) {}
}
