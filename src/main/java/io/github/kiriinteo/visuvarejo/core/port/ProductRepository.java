package io.github.kiriinteo.visuvarejo.core.port;

import io.github.kiriinteo.visuvarejo.core.domain.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(UUID id);

    List<Product> findAll();

    void deleteById(UUID id);

    Product update(Product product);

    Product activate(UUID id);

    Product deactivate(UUID id);

    List<Product> findByCategoryId(UUID categoryId);

    List<Product> findByCompanyId(UUID companyId);
}
