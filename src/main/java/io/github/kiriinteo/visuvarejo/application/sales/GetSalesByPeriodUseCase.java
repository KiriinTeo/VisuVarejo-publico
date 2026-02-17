package io.github.kiriinteo.visuvarejo.application.sales;

import io.github.kiriinteo.visuvarejo.core.domain.Period;
import io.github.kiriinteo.visuvarejo.core.domain.Sale;
import io.github.kiriinteo.visuvarejo.core.port.SaleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GetSalesByPeriodUseCase {

    private final SaleRepository saleRepository;

    public GetSalesByPeriodUseCase(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public List<Sale> execute(LocalDate start, LocalDate end) {
        Period period = new Period(start, end);
        return saleRepository.findByPeriod(period);
    }
}
