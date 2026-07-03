package io.github.kiriinteo.visuvarejo.core.port;

import io.github.kiriinteo.visuvarejo.core.domain.Sale;
import io.github.kiriinteo.visuvarejo.core.domain.Period;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SaleRepository {

    Sale save(Sale sale);

    Optional<Sale> findById(UUID id);

    List<Sale> findByPeriod(Period period);

    List<Sale> findAll();

    List<Sale> findByCompanyId(UUID companyId);
    
    List<Sale> findByCompanyIdAndDateBetween(UUID companyId, LocalDateTime start, LocalDateTime end);

    List<Sale> findAllByCompanyId(UUID companyId);
}
