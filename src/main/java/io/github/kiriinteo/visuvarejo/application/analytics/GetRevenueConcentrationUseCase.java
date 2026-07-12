package io.github.kiriinteo.visuvarejo.application.analytics;

import io.github.kiriinteo.visuvarejo.core.domain.Period;
import io.github.kiriinteo.visuvarejo.core.domain.Sale;
import io.github.kiriinteo.visuvarejo.core.port.SaleRepository;
import io.github.kiriinteo.visuvarejo.infra.security.CurrentUserProvider;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GetRevenueConcentrationUseCase {

    private final SaleRepository saleRepository;
    private final CurrentUserProvider currentUserProvider;

    public GetRevenueConcentrationUseCase(SaleRepository saleRepository, CurrentUserProvider currentUserProvider) {
        this.saleRepository = saleRepository;
        this.currentUserProvider = currentUserProvider;
    }

    public double execute(Period period) {

        List<Sale> sales = saleRepository.findByPeriodAndCompany(period, currentUserProvider.getCompanyId());

        Map<UUID, Double> revenueByProduct = new HashMap<>();

        double totalRevenue = 0;

        for (Sale sale : sales) {
            double value = sale.calculateTotal().getValue().doubleValue();
            totalRevenue += value;

            sale.getItems().forEach(item -> {
                revenueByProduct.merge(
                        item.getProductId(),
                        item.getSubtotal().getValue().doubleValue(),
                        Double::sum
                );
            });
        }

        if (totalRevenue == 0) return 0;

        double maxProductRevenue =
                revenueByProduct.values()
                        .stream()
                        .max(Double::compare)
                        .orElse(0.0);

        return (maxProductRevenue / totalRevenue) * 100;
    }
}