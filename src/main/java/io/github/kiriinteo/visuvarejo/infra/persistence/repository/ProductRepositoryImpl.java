package io.github.kiriinteo.visuvarejo.infra.persistence.repository;

import io.github.kiriinteo.visuvarejo.core.domain.Product;
import io.github.kiriinteo.visuvarejo.core.port.ProductRepository;
import io.github.kiriinteo.visuvarejo.infra.persistence.entity.ProductEntity;
import io.github.kiriinteo.visuvarejo.infra.persistence.mapper.ProductMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository jpaRepository;

    public ProductRepositoryImpl(ProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = ProductMapper.toEntity(product);
        ProductEntity saved = jpaRepository.save(entity);
        return ProductMapper.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(ProductMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(ProductMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Product update(Product product) {
        ProductEntity entity = ProductMapper.toEntity(product);
        ProductEntity updated = jpaRepository.save(entity);
        return ProductMapper.toDomain(updated);
    }

    @Override
    public Product activate(UUID id) {
        ProductEntity entity = jpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        entity.setActive(true);
        ProductEntity updated = jpaRepository.save(entity);
        return ProductMapper.toDomain(updated);
    }

    @Override
    public Product deactivate(UUID id) {
        ProductEntity entity = jpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        entity.setActive(false);
        ProductEntity updated = jpaRepository.save(entity);
        return ProductMapper.toDomain(updated);
    }

    @Override
    public List<Product> findByCategoryId(UUID categoryId) {
        return jpaRepository.findByCategoryId(categoryId)
                .stream()
                .map(ProductMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findByCompanyId(UUID companyId) {
        return jpaRepository.findByCompanyId(companyId)
                .stream()
                .map(ProductMapper::toDomain)
                .collect(Collectors.toList());
    }
}
