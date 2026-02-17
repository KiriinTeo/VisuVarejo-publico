package io.github.kiriinteo.visuvarejo.application.analytics;

import io.github.kiriinteo.visuvarejo.core.analytics.SalesMetrics;
import io.github.kiriinteo.visuvarejo.core.domain.Money;
import io.github.kiriinteo.visuvarejo.core.domain.Sale;
import io.github.kiriinteo.visuvarejo.core.port.SaleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GetSalesMetricsUseCase {

    private final SaleRepository saleRepository;

    public GetSalesMetricsUseCase(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public SalesMetrics execute() {

        List<Sale> sales = saleRepository.findAll();

        Money totalRevenue = new Money(BigDecimal.ZERO);
        int totalItems = 0;

        for (Sale sale : sales) {
            totalRevenue = totalRevenue.add(sale.calculateTotal());
            totalItems += sale.getItems().size();
        }

        return new SalesMetrics(totalRevenue, totalItems);
    }
}
