package io.github.kiriinteo.visuvarejo.application.analytics;

import io.github.kiriinteo.visuvarejo.core.analytics.SalesMetrics;
import io.github.kiriinteo.visuvarejo.core.domain.Money;
import io.github.kiriinteo.visuvarejo.core.domain.Sale;
import io.github.kiriinteo.visuvarejo.core.domain.Period;
import io.github.kiriinteo.visuvarejo.core.port.SaleRepository;

import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class GetSalesMetricsUseCase {

    private final SaleRepository saleRepository;

    public GetSalesMetricsUseCase(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Cacheable(value = "salesMetrics", key = "#companyId + '::' + #period.start + '::' + #period.end")
    public SalesMetrics execute(Period period, UUID companyId) {

        List<Sale> sales = saleRepository.findByPeriodAndCompany(period, companyId);

        Money totalRevenue = new Money(BigDecimal.ZERO);
        int totalItems = 0;

        for (Sale sale : sales) {
            totalRevenue = totalRevenue.add(sale.calculateTotal());
            totalItems += sale.getItems().size();
        }

        int salesCount = sales.size();

        return new SalesMetrics(totalRevenue, totalItems, salesCount);
    }
}
