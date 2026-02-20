package io.github.kiriinteo.visuvarejo.core.port;

import io.github.kiriinteo.visuvarejo.core.domain.Company;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {

    Company save(Company company);

    Optional<Company> findById(UUID id);

    Optional<Company> findByDocument(String document);
}