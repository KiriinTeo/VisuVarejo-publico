package io.github.kiriinteo.visuvarejo.core.port;

import io.github.kiriinteo.visuvarejo.core.domain.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {

    Category save(Category category);

    Optional<Category> findById(UUID id);

    List<Category> findAll();

    void deleteById(UUID id);

    boolean existsById(UUID categoryId);

    List<Category> findByCompanyId(UUID companyId);

    List<Category> findAllByCompanyId(UUID companyId);

    Optional<Category> findByIdAndCompanyId(UUID id, UUID companyId);
}
