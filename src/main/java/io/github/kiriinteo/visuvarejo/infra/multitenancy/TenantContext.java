package io.github.kiriinteo.visuvarejo.infra.multitenancy;

/**
 * Armazena o tenant_id do request atual usando ThreadLocal.
 *
 * Por quê ThreadLocal?
 * Cada request HTTP roda em sua própria thread. O ThreadLocal garante
 * que o tenant_id de um request não vaze para outro — isolamento total
 * sem passar o tenant como parâmetro por toda a cadeia de chamadas.
 *
 * IMPORTANTE: sempre chamar clear() ao fim do request (feito pelo
 * TenantInterceptor) para evitar memory leak em pools de threads.
 */
public class TenantContext {

    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    private TenantContext() {}

    public static void setTenantId(String tenantId) {
        currentTenant.set(tenantId);
    }

    public static String getTenantId() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove();
    }
}
