package io.github.kiriinteo.visuvarejo.application.sales;

import io.github.kiriinteo.visuvarejo.core.domain.Period;
import io.github.kiriinteo.visuvarejo.core.domain.Sale;
import io.github.kiriinteo.visuvarejo.core.port.SaleRepository;
import io.github.kiriinteo.visuvarejo.infra.security.CurrentUserProvider;

import lombok.RequiredArgsConstructor;

//import org.hibernate.annotations.Cache;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetSalesByPeriodUseCase {

    private final SaleRepository saleRepository;

    @Cacheable(value = "salesByPeriod", key = "#start + '::' + #end + '::' + #companyId")
    public List<Sale> execute(LocalDateTime start, LocalDateTime end, UUID companyId) {

        if (start == null || end == null) {
            throw new IllegalArgumentException("Datas de início e fim são obrigatórias");
        }

        if (end.isBefore(start)) {
            throw new IllegalArgumentException("Data final não pode ser anterior à data inicial");
        }

        Period period = new Period(start.toLocalDate(), end.toLocalDate());
        return saleRepository.findByPeriodAndCompany(period, companyId);
    }
}
