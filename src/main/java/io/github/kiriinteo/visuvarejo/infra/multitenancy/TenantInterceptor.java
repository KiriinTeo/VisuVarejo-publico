package io.github.kiriinteo.visuvarejo.infra.multitenancy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor que resolve o tenant_id a partir do JWT de cada request.
 *
 * Fluxo:
 *   1. Spring Security valida o JWT e popula o SecurityContext.
 *   2. Este interceptor lê o claim "tenantId" do token e chama TenantContext.set().
 *   3. Os repositórios usam TenantContext.getTenantId() para filtrar dados.
 *   4. afterCompletion() limpa o ThreadLocal — obrigatório para evitar leak.
 *
 * Por que interceptor e não filtro?
 * O interceptor roda após o filtro de segurança do Spring Security,
 * então o JWT já foi validado quando chegamos aqui. Mais seguro.
 */
@Component
@RequiredArgsConstructor
public class TenantInterceptor implements HandlerInterceptor {

    private final TenantResolver tenantResolver;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String tenantId = tenantResolver.resolveTenantId(request);

        if (tenantId != null) {
            TenantContext.setTenantId(tenantId);
        }

        return true; // continua o processamento do request
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        // Limpeza obrigatória — sem isso, o tenant_id de um request
        // pode vazar para o próximo request na mesma thread do pool.
        TenantContext.clear();
    }
}
