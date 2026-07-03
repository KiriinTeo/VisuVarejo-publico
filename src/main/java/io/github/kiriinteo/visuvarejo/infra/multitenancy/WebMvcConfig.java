package io.github.kiriinteo.visuvarejo.infra.multitenancy;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Registra o TenantInterceptor em todas as rotas exceto /auth/**.
 *
 * /auth/login e /auth/register não têm JWT ainda — não há tenant a resolver.
 * O interceptor é excluído dessas rotas para não quebrar o fluxo de login.
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final TenantInterceptor tenantInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/auth/**",
                        "/swagger-ui/**",
                        "/api-docs/**",
                        "/actuator/**"
                );
    }
}
