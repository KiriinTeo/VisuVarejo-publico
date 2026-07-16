package io.github.kiriinteo.visuvarejo.adapter.web.sales;

import io.github.kiriinteo.visuvarejo.infra.security.CurrentUserProvider;
import io.github.kiriinteo.visuvarejo.application.sales.GetSalesByPeriodUseCase;
import io.github.kiriinteo.visuvarejo.application.sales.RegisterSaleUseCase;
import io.github.kiriinteo.visuvarejo.adapter.web.sales.dto.CreateSaleRequest;
import io.github.kiriinteo.visuvarejo.adapter.web.sales.dto.SaleResponse;
import io.github.kiriinteo.visuvarejo.core.domain.Sale;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private final RegisterSaleUseCase registerSaleUseCase;
    private final GetSalesByPeriodUseCase getSalesByPeriodUseCase;
    private final CurrentUserProvider currentUserProvider;

    public SaleController(RegisterSaleUseCase registerSaleUseCase, 
                        GetSalesByPeriodUseCase getSalesByPeriodUseCase, 
                        CurrentUserProvider currentUserProvider) {
        this.registerSaleUseCase = registerSaleUseCase;
        this.getSalesByPeriodUseCase = getSalesByPeriodUseCase;
        this.currentUserProvider = currentUserProvider;
    }

    @PostMapping
    public SaleResponse create(@RequestBody CreateSaleRequest request) {

        var sale = registerSaleUseCase.execute(
                request.items().stream()
                        .map(i -> new RegisterSaleUseCase.SaleItemRequest(
                                i.productId(),
                                i.quantity()
                        ))
                        .collect(Collectors.toList())
                ,currentUserProvider.getCompanyId()
        );

        return mapToResponse(sale);
    }

    private SaleResponse mapToResponse(Sale sale) {

        return new SaleResponse(
                sale.getId(),
                sale.getDate(),
                sale.getTotalAmount().getValue(),
                sale.getItems().stream()
                        .map(item -> new SaleResponse.Item(
                                item.getProductId(),
                                item.getQuantity(),
                                item.getUnitPrice().getValue()
                                //item.getName() 
                        ))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping
    public List<SaleResponse> getByPeriod(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end
    ) {

        return getSalesByPeriodUseCase.execute(start, end, currentUserProvider.getCompanyId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}
