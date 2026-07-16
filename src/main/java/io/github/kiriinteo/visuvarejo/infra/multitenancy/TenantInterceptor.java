package io.github.kiriinteo.visuvarejo.infra.multitenancy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

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
