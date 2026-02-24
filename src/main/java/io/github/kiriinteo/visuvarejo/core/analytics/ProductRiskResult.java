package io.github.kiriinteo.visuvarejo.core.analytics;

import java.util.UUID;

public class ProductRiskResult {

    private final UUID productId;
    private final double slope;
    private final double volatility;
    private final RiskLevel riskLevel;
    private final String productName;

    public ProductRiskResult(UUID productId,
                             double slope,
                             double volatility,
                             RiskLevel riskLevel,
                             String productName) {
        this.productId = productId;
        this.slope = slope;
        this.volatility = volatility;
        this.riskLevel = riskLevel;
        this.productName = productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getSlope() {
        return slope;
    }

    public double getVolatility() {
        return volatility;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public enum RiskLevel {
        HEALTHY,
        LOW_RISK,
        MEDIUM_RISK,
        HIGH_RISK
    }
}