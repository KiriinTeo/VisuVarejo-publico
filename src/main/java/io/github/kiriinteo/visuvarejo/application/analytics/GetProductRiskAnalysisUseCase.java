package io.github.kiriinteo.visuvarejo.application.analytics;

import io.github.kiriinteo.visuvarejo.core.analytics.ProductRiskAnalyzer;
import io.github.kiriinteo.visuvarejo.core.analytics.ProductRiskResult;
import io.github.kiriinteo.visuvarejo.core.domain.Period;
import io.github.kiriinteo.visuvarejo.core.domain.Sale;
import io.github.kiriinteo.visuvarejo.core.port.SaleRepository;
import io.github.kiriinteo.visuvarejo.infra.security.CurrentUserProvider;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GetProductRiskAnalysisUseCase {

    private final SaleRepository saleRepository;
    private final ProductRiskAnalyzer analyzer;
    private final CurrentUserProvider currentUserProvider;

    public GetProductRiskAnalysisUseCase(SaleRepository saleRepository,
                                         ProductRiskAnalyzer analyzer,
                                         CurrentUserProvider currentUserProvider) {
        this.saleRepository = saleRepository;
        this.analyzer = analyzer;
        this.currentUserProvider = currentUserProvider;
    }

    public List<ProductRiskResult> execute(Period period) {

        List<Sale> sales = saleRepository.findByPeriodAndCompany(period, currentUserProvider.getCompanyId());

        Map<UUID, List<Double>> productDailyTotals = new HashMap<>();

        for (Sale sale : sales) {
            sale.getItems().forEach(item -> {
                productDailyTotals
                        .computeIfAbsent(item.getProductId(), k -> new ArrayList<>())
                        .add(item.getTotal().getValue().doubleValue());
            });
        }

        return productDailyTotals.entrySet()
                .stream()
                .map(e -> analyzer.analyze(e.getKey(), e.getKey().toString(), e.getValue()))
                .sorted(Comparator.comparing(ProductRiskResult::getSlope))
                .collect(Collectors.toList());
    }
}