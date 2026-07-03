package io.github.kiriinteo.visuvarejo.infra.persistence.repository;

import io.github.kiriinteo.visuvarejo.core.domain.Company;
import io.github.kiriinteo.visuvarejo.core.port.CompanyRepository;
import io.github.kiriinteo.visuvarejo.infra.persistence.mapper.CompanyMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CompanyRepositoryImpl implements CompanyRepository {

    private final CompanyJpaRepository jpaRepository;

    public CompanyRepositoryImpl(CompanyJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Company save(Company company) {
        return CompanyMapper.toDomain(
                jpaRepository.save(CompanyMapper.toEntity(company))
        );
    }

    @Override
    public Optional<Company> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(CompanyMapper::toDomain);
    }

    @Override
    public Optional<Company> findByDocument(String document) {
        return jpaRepository.findByDocument(document)
                .map(CompanyMapper::toDomain);
    }
    
}