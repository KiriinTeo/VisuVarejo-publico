package io.github.kiriinteo.visuvarejo.adapter.web.catalog.dto;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        UUID companyId
) {}
