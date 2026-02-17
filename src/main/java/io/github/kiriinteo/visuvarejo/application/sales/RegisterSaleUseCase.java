package io.github.kiriinteo.visuvarejo.application.sales;

import io.github.kiriinteo.visuvarejo.core.domain.*;
import io.github.kiriinteo.visuvarejo.core.port.ProductRepository;
import io.github.kiriinteo.visuvarejo.core.port.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RegisterSaleUseCase {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

    public RegisterSaleUseCase(ProductRepository productRepository,
                               SaleRepository saleRepository) {
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
    }

    public Sale execute(List<SaleItemRequest> itemsRequest) {

        Sale sale = new Sale(UUID.randomUUID());

        for (SaleItemRequest itemRequest : itemsRequest) {

            Product product = productRepository
                    .findById(itemRequest.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            SaleItem item = new SaleItem(product, itemRequest.quantity());
            sale.addItem(item);
        }

        return saleRepository.save(sale);
    }

    public record SaleItemRequest(UUID productId, int quantity) {}
}
