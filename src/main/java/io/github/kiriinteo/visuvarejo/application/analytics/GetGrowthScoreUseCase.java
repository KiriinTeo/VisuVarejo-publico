package io.github.kiriinteo.visuvarejo.application.analytics;

import io.github.kiriinteo.visuvarejo.core.analytics.*;
import io.github.kiriinteo.visuvarejo.core.domain.Period;
import io.github.kiriinteo.visuvarejo.core.domain.Sale;
import io.github.kiriinteo.visuvarejo.core.port.SaleRepository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GetGrowthScoreUseCase {

    private final SaleRepository saleRepository;
    private final ProductRiskAnalyzer riskAnalyzer;
    private final GrowthScoreEngine scoreEngine;

    public GetGrowthScoreUseCase(SaleRepository saleRepository,
                                 ProductRiskAnalyzer riskAnalyzer,
                                 GrowthScoreEngine scoreEngine
                                  ) {
        this.saleRepository = saleRepository;
        this.riskAnalyzer = riskAnalyzer;
        this.scoreEngine = scoreEngine;
    }

    @Cacheable(value = "growthScores", key = "#companyId + '::' + #period.start + '::' + #period.end")
    public List<GrowthScoreResult> execute(UUID companyId, Period period) {

        List<Sale> sales = saleRepository.findByPeriodAndCompany(period, companyId);

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