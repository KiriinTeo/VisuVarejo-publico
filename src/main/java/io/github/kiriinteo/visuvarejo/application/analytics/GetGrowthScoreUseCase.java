package io.github.kiriinteo.visuvarejo.application.analytics;

import io.github.kiriinteo.visuvarejo.core.analytics.*;
import io.github.kiriinteo.visuvarejo.core.domain.Period;
import io.github.kiriinteo.visuvarejo.core.domain.Sale;
import io.github.kiriinteo.visuvarejo.core.port.SaleRepository;
import io.github.kiriinteo.visuvarejo.infra.security.CurrentUserProvider;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GetGrowthScoreUseCase {

    private final SaleRepository saleRepository;
    private final ProductRiskAnalyzer riskAnalyzer;
    private final GrowthScoreEngine scoreEngine;
    private final CurrentUserProvider currentUserProvider;

    public GetGrowthScoreUseCase(SaleRepository saleRepository,
                                 ProductRiskAnalyzer riskAnalyzer,
                                 GrowthScoreEngine scoreEngine,
                                 CurrentUserProvider currentUserProvider) {
        this.saleRepository = saleRepository;
        this.riskAnalyzer = riskAnalyzer;
        this.scoreEngine = scoreEngine;
        this.currentUserProvider = currentUserProvider;
    }

    public List<GrowthScoreResult> execute(Period period) {

        List<Sale> sales = saleRepository.findByPeriodAndCompany(period, currentUserProvider.getCompanyId());

        Map<String, List<Double>> productTotals = new HashMap<>();

        for (Sale sale : sales) {
            sale.getItems().forEach(item -> {
                productTotals
                        .computeIfAbsent(item.getProductId().toString(), k -> new ArrayList<>())
                        .add(item.getTotal().getValue().doubleValue());
            });
        }

        return productTotals.entrySet()
                .stream()
                .map(entry -> {
                    ProductRiskResult risk =
                            riskAnalyzer.analyze(UUID.fromString(entry.getKey()), entry.getKey(), entry.getValue());

                    return scoreEngine.calculate(
                            entry.getKey(),
                            entry.getValue(),
                            risk.getSlope(),
                            risk.getVolatility()
                    );
                })
                .sorted(Comparator.comparing(GrowthScoreResult::getGrowthScore).reversed())
                .collect(Collectors.toList());
    }
}