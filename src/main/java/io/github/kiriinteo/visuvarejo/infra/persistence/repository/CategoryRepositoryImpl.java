package io.github.kiriinteo.visuvarejo.infra.persistence.repository;

import io.github.kiriinteo.visuvarejo.core.domain.Category;
import io.github.kiriinteo.visuvarejo.core.port.CategoryRepository;
import io.github.kiriinteo.visuvarejo.infra.persistence.entity.CategoryEntity;
import io.github.kiriinteo.visuvarejo.infra.persistence.mapper.CategoryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository jpaRepository;

    public CategoryRepositoryImpl(CategoryJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Category save(Category category) {
        CategoryEntity entity = CategoryMapper.toEntity(category);
        CategoryEntity saved = jpaRepository.save(entity);
        return CategoryMapper.toDomain(saved);
    }

    @Override
    public Optional<Category> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(CategoryMapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(CategoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID categoryId) {
        return jpaRepository.existsById(categoryId);
    }

    @Override
    public List<Category> findByCompanyId(UUID companyId) {
        return jpaRepository.findByCompanyId(companyId)
                .stream()
                .map(CategoryMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Category> findAllByCompanyId(UUID companyId) {
        return jpaRepository.findAllByCompanyId(companyId)
                .stream()
                .map(CategoryMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Category> findByIdAndCompanyId(UUID id, UUID companyId) {       
        return jpaRepository.findById(id)
                .filter(categoryEntity -> categoryEntity.getCompanyId().equals(companyId))
                .map(CategoryMapper::toDomain);
    }
}
