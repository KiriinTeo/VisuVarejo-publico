package io.github.kiriinteo.visuvarejo.core.analytics;

import java.util.List;
import java.util.UUID;

public class ProductRiskAnalyzer {

    public ProductRiskResult analyze(UUID productId, String productName, List<Double> values) {

        if (values.size() < 2) {
            return new ProductRiskResult(
                productId,
                0.0,
                0.0,
                ProductRiskResult.RiskLevel.HEALTHY,
                productName
            );
        }

        int n = values.size();

        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumX2 = 0;

        for (int i = 0; i < n; i++) {
            double x = i;
            double y = values.get(i);

            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }

        double slope = (n * sumXY - sumX * sumY) /
                (n * sumX2 - sumX * sumX);

        double mean = sumY / n;

        double variance = 0;
        for (double v : values) {
            variance += Math.pow(v - mean, 2);
        }

        double volatility = Math.sqrt(variance / n);

        ProductRiskResult.RiskLevel level;

        if (slope < -0.05) {
            level = ProductRiskResult.RiskLevel.HIGH_RISK;
        } else if (slope < -0.02) {
            level = ProductRiskResult.RiskLevel.MEDIUM_RISK;
        } else if (slope < 0) {
            level = ProductRiskResult.RiskLevel.LOW_RISK;
        } else {
            level = ProductRiskResult.RiskLevel.HEALTHY;
        }

        return new ProductRiskResult(productId,slope, volatility, level, productName);
    }
}