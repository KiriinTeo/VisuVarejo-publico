package io.github.kiriinteo.visuvarejo.application.analytics;

import io.github.kiriinteo.visuvarejo.core.domain.Period;
import io.github.kiriinteo.visuvarejo.core.domain.Sale;
import io.github.kiriinteo.visuvarejo.core.port.SaleRepository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GetRevenueConcentrationUseCase {

    private final SaleRepository saleRepository;

    public GetRevenueConcentrationUseCase(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Cacheable(value = "revenueConcentration", key = "#period.start + '::' + #period.end + '::' + #companyId")
    public double execute(Period period, UUID companyId) {

        List<Sale> sales = saleRepository.findByPeriodAndCompany(period, companyId);

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