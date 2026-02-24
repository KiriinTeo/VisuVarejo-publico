package io.github.kiriinteo.visuvarejo.adapter.web.analytics.dto;

import io.github.kiriinteo.visuvarejo.core.alerts.Alert;

import java.util.List;

public record DashboardResponse(
        OverviewResponse overview,
        double trend,
        int healthScore,
        double revenueConcentration,
        List<Alert> alerts
) {}