package io.github.kiriinteo.visuvarejo.adapter.web.analytics.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TicketAverageResponse(
        LocalDateTime start,
        LocalDateTime end,
        BigDecimal averageTicket
) {}