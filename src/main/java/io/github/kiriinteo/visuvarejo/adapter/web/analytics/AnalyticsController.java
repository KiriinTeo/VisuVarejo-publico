package io.github.kiriinteo.visuvarejo.adapter.web.analytics;

import io.github.kiriinteo.visuvarejo.core.alerts.Alert;
import io.github.kiriinteo.visuvarejo.core.domain.Period;
import io.github.kiriinteo.visuvarejo.application.analytics.GetRevenueConcentrationUseCase;
import io.github.kiriinteo.visuvarejo.application.analytics.GetStoreHealthScoreUseCase;
import io.github.kiriinteo.visuvarejo.application.analytics.AlertGenerationUseCase;
import io.github.kiriinteo.visuvarejo.application.analytics.GetSalesMetricsUseCase;
import io.github.kiriinteo.visuvarejo.application.analytics.GetTrendAnalysisUseCase;
import io.github.kiriinteo.visuvarejo.application.analytics.GetProductRiskAnalysisUseCase;
import io.github.kiriinteo.visuvarejo.application.analytics.GetAverageTicketUseCase;
import io.github.kiriinteo.visuvarejo.application.analytics.GetRevenueByPeriodUseCase;
import io.github.kiriinteo.visuvarejo.application.analytics.GetGrowthScoreUseCase;
import io.github.kiriinteo.visuvarejo.adapter.web.analytics.dto.ProductRiskResponse;
import io.github.kiriinteo.visuvarejo.adapter.web.analytics.dto.RevenueResponse;
import io.github.kiriinteo.visuvarejo.adapter.web.analytics.dto.TicketAverageResponse;
import io.github.kiriinteo.visuvarejo.adapter.web.analytics.dto.DashboardResponse;
import io.github.kiriinteo.visuvarejo.adapter.web.analytics.dto.GrowthScoreResponse;
import io.github.kiriinteo.visuvarejo.adapter.web.analytics.dto.OverviewResponse;
import io.github.kiriinteo.visuvarejo.adapter.web.analytics.dto.DashboardResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final GetRevenueByPeriodUseCase getRevenueByPeriodUseCase;
    private final GetAverageTicketUseCase getAverageTicketUseCase;
    private final GetProductRiskAnalysisUseCase getProductRiskAnalysisUseCase;
    private final GetGrowthScoreUseCase getGrowthScoreUseCase;
    private final GetSalesMetricsUseCase getSalesMetricsUseCase;
    private final GetTrendAnalysisUseCase getTrendAnalysisUseCase;
    private final AlertGenerationUseCase alertGenerationUseCase;
    private final GetStoreHealthScoreUseCase storeHealthScoreUseCase;
    private final GetRevenueConcentrationUseCase revenueConcentrationUseCase;

        public AnalyticsController(GetRevenueByPeriodUseCase getRevenueByPeriodUseCase, GetAverageTicketUseCase getAverageTicketUseCase, 
                GetProductRiskAnalysisUseCase getProductRiskAnalysisUseCase, GetGrowthScoreUseCase getGrowthScoreUseCase, 
                GetSalesMetricsUseCase getSalesMetricsUseCase, GetTrendAnalysisUseCase getTrendAnalysisUseCase, 
                AlertGenerationUseCase alertGenerationUseCase, GetStoreHealthScoreUseCase storeHealthScoreUseCase, 
                GetRevenueConcentrationUseCase revenueConcentrationUseCase) {

        this.getRevenueByPeriodUseCase = getRevenueByPeriodUseCase;
        this.getAverageTicketUseCase = getAverageTicketUseCase;
        this.getProductRiskAnalysisUseCase = getProductRiskAnalysisUseCase;
        this.getGrowthScoreUseCase = getGrowthScoreUseCase;
        this.getSalesMetricsUseCase = getSalesMetricsUseCase;
        this.getTrendAnalysisUseCase = getTrendAnalysisUseCase;
        this.alertGenerationUseCase = alertGenerationUseCase;
        this.storeHealthScoreUseCase = storeHealthScoreUseCase;
        this.revenueConcentrationUseCase = revenueConcentrationUseCase;
    }

        @GetMapping("/revenue")
        public RevenueResponse getRevenue(
                @RequestParam LocalDateTime start,
                @RequestParam LocalDateTime end
        ) {
                BigDecimal totalRevenue = getRevenueByPeriodUseCase.execute(start, end);
                return new RevenueResponse(start, end, totalRevenue);
        }
        
        @GetMapping("/ticket-average")
        public TicketAverageResponse getAverageTicket(
                @RequestParam LocalDateTime start,
                @RequestParam LocalDateTime end
        ) {
                BigDecimal averageTicket = getAverageTicketUseCase.execute(start, end);
                return new TicketAverageResponse(start, end, averageTicket);
        }

        @GetMapping("/products/risk")
        public List<ProductRiskResponse> getProductRisk(
                @RequestParam LocalDateTime start,
                @RequestParam LocalDateTime end
        ) {

                Period period = new Period(start.toLocalDate(), end.toLocalDate());

                return getProductRiskAnalysisUseCase.execute(period)
                        .stream()
                        .map(r -> new ProductRiskResponse(
                                r.getProductId().toString(),
                                r.getProductName(),
                                r.getSlope(),
                                r.getVolatility(),
                                r.getRiskLevel()
                        ))
                        .toList();
        }

        @GetMapping("/products/growth-score")
        public List<GrowthScoreResponse> getGrowthScore(
                @RequestParam LocalDateTime start,
                @RequestParam LocalDateTime end
        ) {

                Period period = new Period(start.toLocalDate(), end.toLocalDate());

                return getGrowthScoreUseCase.execute(period)
                        .stream()
                        .map(r -> new GrowthScoreResponse(
                                r.getProductId(),
                                r.getGrowthScore(),
                                r.getSlope(),
                                r.getAverageRevenue(),
                                r.getVolatility()
                        ))
                        .toList();
        }

        @GetMapping("/overview")
        public OverviewResponse getOverview(
                @RequestParam LocalDateTime start,
                @RequestParam LocalDateTime end
        ) {

        Period period = new Period(start.toLocalDate(), end.toLocalDate());

        var metrics = getSalesMetricsUseCase.execute(period);

        return new OverviewResponse(
                start,
                end,
                metrics.getTotalRevenue().getValue().doubleValue(),
                metrics.getTotalItemsSold(),
                metrics.getSalesCount(),
                metrics.getAverageTicket()
        );
        }

        @GetMapping("/trend-comparison")
        public double getTrendComparison(
                @RequestParam LocalDateTime previousStart,
                @RequestParam LocalDateTime previousEnd,
                @RequestParam LocalDateTime currentStart,
                @RequestParam LocalDateTime currentEnd
        ) {

                return getTrendAnalysisUseCase.execute(
                        previousStart.toLocalDate(),
                        previousEnd.toLocalDate(),
                        currentStart.toLocalDate(),
                        currentEnd.toLocalDate()
                );
        }

        @GetMapping("/alerts")
        public List<Alert> getAlerts(
                @RequestParam LocalDateTime previousStart,
                @RequestParam LocalDateTime previousEnd,
                @RequestParam LocalDateTime currentStart,
                @RequestParam LocalDateTime currentEnd
        ) {

                return alertGenerationUseCase.execute(
                        previousStart.toLocalDate(),
                        previousEnd.toLocalDate(),
                        currentStart.toLocalDate(),
                        currentEnd.toLocalDate()
                );
        }

        @GetMapping("/dashboard")
        public DashboardResponse getDashboard(
                @RequestParam LocalDateTime previousStart,
                @RequestParam LocalDateTime previousEnd,
                @RequestParam LocalDateTime currentStart,
                @RequestParam LocalDateTime currentEnd
        ) {

        Period currentPeriod =
                new Period(currentStart.toLocalDate(), currentEnd.toLocalDate());

        var overview = getOverview(currentStart, currentEnd);

        double trend = getTrendAnalysisUseCase.execute(
                previousStart.toLocalDate(),
                previousEnd.toLocalDate(),
                currentStart.toLocalDate(),
                currentEnd.toLocalDate()
        );

        double concentration =
                revenueConcentrationUseCase.execute(currentPeriod);

        int healthScore =
                storeHealthScoreUseCase.execute(
                        trend,
                        0, // pode evoluir depois
                        concentration
                );

        var alerts =
                alertGenerationUseCase.execute(
                        previousStart.toLocalDate(),
                        previousEnd.toLocalDate(),
                        currentStart.toLocalDate(),
                        currentEnd.toLocalDate()
                );

        return new DashboardResponse(
                overview,
                trend,
                healthScore,
                concentration,
                alerts
        );
        }
}
