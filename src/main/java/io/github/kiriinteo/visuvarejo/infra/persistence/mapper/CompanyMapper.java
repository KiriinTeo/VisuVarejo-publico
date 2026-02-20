package io.github.kiriinteo.visuvarejo.infra.persistence.mapper;

import io.github.kiriinteo.visuvarejo.core.domain.Company;
import io.github.kiriinteo.visuvarejo.infra.persistence.entity.CompanyEntity;

public class CompanyMapper {

    public static CompanyEntity toEntity(Company company) {
        return new CompanyEntity(
                company.getId(),
                company.getName(),
                company.getDocument()
        );
    }

    public static Company toDomain(CompanyEntity entity) {
        return new Company(
                entity.getId(),
                entity.getName(),
                entity.getDocument()
        );
    }
}