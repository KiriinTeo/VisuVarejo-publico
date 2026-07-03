package io.github.kiriinteo.visuvarejo.infra.persistence.repository;

import io.github.kiriinteo.visuvarejo.core.domain.Period;
import io.github.kiriinteo.visuvarejo.core.domain.Sale;
import io.github.kiriinteo.visuvarejo.core.port.SaleRepository;
import io.github.kiriinteo.visuvarejo.infra.persistence.entity.SaleEntity;
import io.github.kiriinteo.visuvarejo.infra.persistence.mapper.SaleMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class SaleRepositoryImpl implements SaleRepository {

    private final SaleJpaRepository jpaRepository;

    public SaleRepositoryImpl(SaleJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Sale save(Sale sale) {
        SaleEntity entity = SaleMapper.toEntity(sale);
        SaleEntity saved = jpaRepository.save(entity);
        return SaleMapper.toDomain(saved);
    }

    @Override
    public Optional<Sale> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(SaleMapper::toDomain);
    }

    @Override
    public List<Sale> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(SaleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Sale> findByPeriod(Period period) {
        LocalDateTime start = period.getStart().atStartOfDay();
        LocalDateTime end = period.getEnd().atTime(23, 59, 59);

        return jpaRepository.findByDateBetween(start, end)
                .stream()
                .map(SaleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Sale> findByCompanyId(UUID companyId) { 
        return jpaRepository.findByCompanyId(companyId)
                .stream()
                .map(SaleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Sale> findByCompanyIdAndDateBetween(UUID companyId, LocalDateTime start, LocalDateTime end) {
        return jpaRepository.findByCompanyIdAndDateBetween(companyId, start, end)
                .stream()
                .map(SaleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Sale> findAllByCompanyId(UUID companyId) {
        return jpaRepository.findAll()
                .stream()
                .filter(saleEntity -> companyId.equals(saleEntity.getCompanyId()))
                .map(SaleMapper::toDomain)
                .collect(Collectors.toList());
    }
    
}
