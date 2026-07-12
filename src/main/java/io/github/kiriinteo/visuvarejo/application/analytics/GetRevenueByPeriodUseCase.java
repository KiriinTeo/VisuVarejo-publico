package io.github.kiriinteo.visuvarejo.application.analytics;

import io.github.kiriinteo.visuvarejo.core.domain.Period;
import io.github.kiriinteo.visuvarejo.core.domain.Sale;
import io.github.kiriinteo.visuvarejo.core.port.SaleRepository;
import io.github.kiriinteo.visuvarejo.infra.security.CurrentUserProvider;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GetRevenueByPeriodUseCase {

    private final SaleRepository saleRepository;
    private final CurrentUserProvider currentUserProvider;

    public GetRevenueByPeriodUseCase(SaleRepository saleRepository, CurrentUserProvider currentUserProvider) {
        this.saleRepository = saleRepository;
        this.currentUserProvider = currentUserProvider;
    }

    public BigDecimal execute(LocalDateTime start, LocalDateTime end) {

        if (start == null || end == null) {
            throw new IllegalArgumentException("Datas de início e fim são obrigatórias");
        }

        if (end.isBefore(start)) {
            throw new IllegalArgumentException("Data final não pode ser anterior à data inicial");
        }

        Period period = new Period(start.toLocalDate(), end.toLocalDate());
        List<Sale> sales = saleRepository.findByPeriodAndCompany(period, currentUserProvider.getCompanyId());

        return sales.stream()
                .map(sale -> sale.getTotalAmount().getValue())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
