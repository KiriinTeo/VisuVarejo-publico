package io.github.kiriinteo.visuvarejo.infra.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentUserProvider {

    public String getEmail() {

        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof AuthenticatedUser user) {
            return user.getEmail();
        }

        return null;
    }

    public UUID getCompanyId() {

        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof AuthenticatedUser user) {
            return user.getCompanyId();
        }

        throw new RuntimeException("Id da empresa autenticada não encontrado");
    }

    public String getRole() {

        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof AuthenticatedUser user) {
            return user.getRole();
        }

        return null;
    }
}