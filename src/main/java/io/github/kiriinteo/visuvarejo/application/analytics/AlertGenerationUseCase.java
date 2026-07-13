package io.github.kiriinteo.visuvarejo.application.analytics;

import io.github.kiriinteo.visuvarejo.core.alerts.Alert;
import io.github.kiriinteo.visuvarejo.core.alerts.AlertType;
import io.github.kiriinteo.visuvarejo.core.analytics.ProductRiskResult;
import io.github.kiriinteo.visuvarejo.core.domain.Period;
import io.github.kiriinteo.visuvarejo.infra.security.CurrentUserProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertGenerationUseCase {

    private final GetProductRiskAnalysisUseCase riskUseCase;
    private final GetTrendAnalysisUseCase trendUseCase;
    private final CurrentUserProvider currentUserProvider;

    public AlertGenerationUseCase(
            GetProductRiskAnalysisUseCase riskUseCase,
            GetTrendAnalysisUseCase trendUseCase,
            CurrentUserProvider currentUserProvider
    ) {
        this.riskUseCase = riskUseCase;
        this.trendUseCase = trendUseCase;
        this.currentUserProvider = currentUserProvider;
    }

    public List<Alert> execute(
            LocalDate previousStart,
            LocalDate previousEnd,
            LocalDate currentStart,
            LocalDate currentEnd
    ) {

        List<Alert> alerts = new ArrayList<>();

        Period currentPeriod = new Period(currentStart, currentEnd);

        List<ProductRiskResult> risks =
                riskUseCase.execute(currentUserProvider.getCompanyId(), currentPeriod);

        for (ProductRiskResult risk : risks) {

            if (risk.getRiskLevel() == ProductRiskResult.RiskLevel.HIGH_RISK) {
                alerts.add(new Alert(
                        AlertType.HIGH_RISK_PRODUCT,
                        String.format("Produto %s apresenta alto risco de queda vertiginosa nas vendas",
                                risk.getProductName()),
                        "HIGH",
                        risk.getProductId(),
                        risk.getProductName()
                ));
            }

            if (risk.getRiskLevel() == ProductRiskResult.RiskLevel.MEDIUM_RISK) {
                alerts.add(new Alert(
                        AlertType.MEDIUM_RISK_PRODUCT,
                        String.format("Produto %s apresenta declínio consistente nas vendas",
                                risk.getProductName()),
                        "MEDIUM",
                        risk.getProductId(),
                        risk.getProductName()
                ));
            }

            if (risk.getRiskLevel() == ProductRiskResult.RiskLevel.LOW_RISK) {
                alerts.add(new Alert(
                        AlertType.LOW_RISK_PRODUCT,
                        String.format("Produto %s apresenta baixo risco de baixa performance, mas deve ser monitorado",
                                risk.getProductName()),
                        "LOW",
                        risk.getProductId(),
                        risk.getProductName()
                ));
            }

            if (risk.getRiskLevel() == ProductRiskResult.RiskLevel.HEALTHY) {
                alerts.add(new Alert(
                        AlertType.STABLE_PRODUCT,
                        String.format("Produto %s apresenta risco mínimo de baixa performance",
                                risk.getProductName()),
                        "NONE",
                        risk.getProductId(),
                        risk.getProductName()
                ));
            }
        }

        double trend = trendUseCase.execute(
                previousStart,
                previousEnd,
                currentStart,
                currentEnd
        );

        if (trend < -15) {
            alerts.add(new Alert(
                    AlertType.GLOBAL_DECLINE,
                    String.format("Vendas caíram mais de 15% comparado ao período anterior"),
                    "HIGH",
                    null,
                    null     
            ));
        }

        if (trend > 40) {
            alerts.add(new Alert(
                    AlertType.ANOMALOUS_GROWTH,
                    "Vendas aumentaram mais de 40% comparado ao período anterior",
                    "POSITIVE",
                    null,
                    null
            ));
        }

        return alerts;
    }
}