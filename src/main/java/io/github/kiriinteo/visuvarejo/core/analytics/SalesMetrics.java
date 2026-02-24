package io.github.kiriinteo.visuvarejo.core.analytics;

import io.github.kiriinteo.visuvarejo.core.domain.Money;

public class SalesMetrics {

    private final Money totalRevenue;
    private final int totalItemsSold;
    private final double averageTicket;
    private final int salesCount;

    public SalesMetrics(Money totalRevenue, int totalItemsSold, int salesCount) {
        this.totalRevenue = totalRevenue;
        this.totalItemsSold = totalItemsSold;
        this.salesCount = salesCount == 0 ? 0 : salesCount;

        this.averageTicket = totalItemsSold == 0
                ? 0
                : totalRevenue.getValue().doubleValue() / salesCount;
    }

    public Money getTotalRevenue() {
        return totalRevenue;
    }

    public int getTotalItemsSold() {
        return totalItemsSold;
    }

    public double getAverageTicket() {
        return averageTicket;
    }

    public int getSalesCount() {
        return salesCount;
    }
}
