package io.github.kiriinteo.visuvarejo.infra.persistence.repository;

//import io.github.kiriinteo.visuvarejo.core.domain.Sale;
import io.github.kiriinteo.visuvarejo.infra.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, UUID> {
    List<CategoryEntity> findByCompanyId(UUID companyId);

    List<CategoryEntity> findAllByCompanyId(UUID companyId);
}
