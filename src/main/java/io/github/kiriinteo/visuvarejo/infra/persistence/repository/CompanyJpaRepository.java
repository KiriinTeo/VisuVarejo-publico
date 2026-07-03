package io.github.kiriinteo.visuvarejo.infra.persistence.repository;

import io.github.kiriinteo.visuvarejo.infra.persistence.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
// import java.util.List;

public interface CompanyJpaRepository extends JpaRepository<CompanyEntity, UUID> {
    Optional<CompanyEntity> findByDocument(String document);

}