package io.github.kiriinteo.visuvarejo.adapter.web.auth.dto;

public record RegisterRequest(
        String name,
        String email,
        String password,
        String companyName,
        String companyDocument,
        String tenantId
) {}