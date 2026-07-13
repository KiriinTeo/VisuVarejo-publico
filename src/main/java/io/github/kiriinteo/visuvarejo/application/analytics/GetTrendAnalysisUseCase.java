package io.github.kiriinteo.visuvarejo.application.analytics;

import io.github.kiriinteo.visuvarejo.core.analytics.TrendAnalyzer;
import io.github.kiriinteo.visuvarejo.core.domain.Period;
import io.github.kiriinteo.visuvarejo.core.domain.Sale;
import io.github.kiriinteo.visuvarejo.core.port.SaleRepository;
import io.github.kiriinteo.visuvarejo.infra.security.CurrentUserProvider;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GetTrendAnalysisUseCase {

    private final SaleRepository saleRepository;
    private final TrendAnalyzer trendAnalyzer;
    private final CurrentUserProvider currentUserProvider;

    public GetTrendAnalysisUseCase(SaleRepository saleRepository, CurrentUserProvider currentUserProvider) {
        this.saleRepository = saleRepository;
        this.trendAnalyzer = new TrendAnalyzer();
        this.currentUserProvider = currentUserProvider;
    }

    @Cacheable(value = "trendAnalysis", key = "#previousStart + '::' + #previousEnd + '::' + #currentStart + '::' + #currentEnd")
    public double execute(LocalDate previousStart,
                          LocalDate previousEnd,
                          LocalDate currentStart,
                          LocalDate currentEnd) {

        Period previousPeriod = new Period(previousStart, previousEnd);
        Period currentPeriod = new Period(currentStart, currentEnd);

        double previousTotal = calculateTotalRevenue(previousPeriod);
        double currentTotal = calculateTotalRevenue(currentPeriod);

        return trendAnalyzer.calculateGrowthRate(previousTotal, currentTotal);
    }

    @Cacheable(value = "totalRevenue", key = "#period.start + '::' + #period.end")
    private double calculateTotalRevenue(Period period) {

        List<Sale> sales = saleRepository.findByPeriodAndCompany(period, currentUserProvider.getCompanyId());

        return sales.stream()
                .map(sale -> sale.calculateTotal().getValue().doubleValue())
                .reduce(0.0, Double::sum);
    }
}
