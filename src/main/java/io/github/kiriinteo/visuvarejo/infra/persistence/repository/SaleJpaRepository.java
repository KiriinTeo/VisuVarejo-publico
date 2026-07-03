package io.github.kiriinteo.visuvarejo.infra.persistence.repository;

import io.github.kiriinteo.visuvarejo.infra.persistence.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SaleJpaRepository extends JpaRepository<SaleEntity, UUID> {
    List<SaleEntity> findByCompanyId(UUID companyId);

    List<SaleEntity> findByDateBetween(LocalDateTime start, LocalDateTime end);

    List<SaleEntity> findByCompanyIdAndDateBetween(UUID companyId, LocalDateTime start, LocalDateTime end);
}
