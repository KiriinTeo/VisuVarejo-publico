package io.github.kiriinteo.visuvarejo.adapter.web.analytics;

import io.github.kiriinteo.visuvarejo.application.analytics.GetAverageTicketUseCase;
import io.github.kiriinteo.visuvarejo.application.analytics.GetRevenueByPeriodUseCase;
import io.github.kiriinteo.visuvarejo.adapter.web.analytics.dto.RevenueResponse;
import io.github.kiriinteo.visuvarejo.adapter.web.analytics.dto.TicketAverageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final GetRevenueByPeriodUseCase getRevenueByPeriodUseCase;
    private final GetAverageTicketUseCase getAverageTicketUseCase;

    public AnalyticsController(GetRevenueByPeriodUseCase getRevenueByPeriodUseCase, GetAverageTicketUseCase getAverageTicketUseCase) {
        this.getRevenueByPeriodUseCase = getRevenueByPeriodUseCase;
        this.getAverageTicketUseCase = getAverageTicketUseCase;
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
}
