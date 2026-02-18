package io.github.kiriinteo.visuvarejo.adapter.web.catalog.dto;

import java.math.BigDecimal;

public record UpdateProductRequest(
        String name,
        BigDecimal price,
        Boolean active
) {}
