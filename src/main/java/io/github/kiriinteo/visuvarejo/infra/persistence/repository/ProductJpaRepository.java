package io.github.kiriinteo.visuvarejo.infra.persistence.repository;

import io.github.kiriinteo.visuvarejo.infra.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findByCategoryId(UUID categoryId);
    List<ProductEntity> findByCompanyId(UUID companyId);
}
