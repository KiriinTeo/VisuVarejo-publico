package io.github.kiriinteo.visuvarejo.infra.multitenancy;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * Extrai o tenantId do claim "tenantId" presente no JWT.
 *
 * O token já foi validado pelo filtro do Spring Security antes
 * de chegar aqui — apenas fazemos a leitura do claim.
 *
 * O claim "tenantId" deve ser adicionado ao JWT no momento
 * do login, na classe de geração de token existente no projeto.
 */
@Component
public class TenantResolver {

    private final Key signingKey;

    public TenantResolver(@Value("${jwt.secret}") String secret) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String resolveTenantId(HttpServletRequest request) {
        String token = extractToken(request);

        if (token == null) {
            return null;
        }

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get("tenantId", String.class);

        } catch (Exception e) {
            // Token inválido é problema do Spring Security, não daqui.
            return null;
        }
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }

        return null;
    }
}
