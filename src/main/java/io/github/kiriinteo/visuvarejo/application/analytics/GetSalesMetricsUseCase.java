package io.github.kiriinteo.visuvarejo.application.analytics;

import io.github.kiriinteo.visuvarejo.core.analytics.SalesMetrics;
import io.github.kiriinteo.visuvarejo.core.domain.Money;
import io.github.kiriinteo.visuvarejo.core.domain.Sale;
import io.github.kiriinteo.visuvarejo.core.domain.Period;
import io.github.kiriinteo.visuvarejo.core.port.SaleRepository;
import io.github.kiriinteo.visuvarejo.infra.security.CurrentUserProvider;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GetSalesMetricsUseCase {

    private final SaleRepository saleRepository;
    private final CurrentUserProvider currentUserProvider;

    public GetSalesMetricsUseCase(SaleRepository saleRepository, CurrentUserProvider currentUserProvider) {
        this.saleRepository = saleRepository;
        this.currentUserProvider = currentUserProvider;
    }

    public SalesMetrics execute(Period period) {

        List<Sale> sales = saleRepository.findByPeriodAndCompany(period, currentUserProvider.getCompanyId());

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
