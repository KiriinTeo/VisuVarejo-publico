package io.github.kiriinteo.visuvarejo.adapter.web.catalog.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        BigDecimal price,
        boolean active
) {}
