-- ============================================================
-- V1__init_schema.sql
-- Schema inicial do VisuVarejo com multi-tenancy via tenant_id.
--
-- Estratégia: shared schema com discriminador (tenant_id em cada
-- tabela). Simples de implementar, fácil de migrar para
-- schema-per-tenant no futuro se necessário.
-- ============================================================

-- ── Usuários ──────────────────────────────────────────────────────────────
-- tenant_id identifica a qual loja/empresa este usuário pertence.
CREATE TABLE users (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id   VARCHAR(100) NOT NULL,
    name        VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    role        VARCHAR(50)  NOT NULL DEFAULT 'VENDEDOR',
    created_at  TIMESTAMP    NOT NULL DEFAULT now(),
    CONSTRAINT uq_users_email_tenant UNIQUE (email, tenant_id)
);

-- ── Categorias ────────────────────────────────────────────────────────────
CREATE TABLE categories (
    id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id VARCHAR(100) NOT NULL,
    name      VARCHAR(255) NOT NULL,
    CONSTRAINT uq_categories_name_tenant UNIQUE (name, tenant_id)
);

-- ── Produtos ─────────────────────────────────────────────────────────────
CREATE TABLE products (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id   VARCHAR(100)   NOT NULL,
    name        VARCHAR(255)   NOT NULL,
    price       NUMERIC(15, 2) NOT NULL,
    active      BOOLEAN        NOT NULL DEFAULT TRUE,
    category_id UUID           NOT NULL REFERENCES categories(id),
    CONSTRAINT uq_products_name_tenant UNIQUE (name, tenant_id)
);

-- ── Vendas ────────────────────────────────────────────────────────────────
CREATE TABLE sales (
    id           UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id    VARCHAR(100)   NOT NULL,
    total_amount NUMERIC(15, 2) NOT NULL,
    sale_date    TIMESTAMP      NOT NULL DEFAULT now()
);

-- ── Itens de venda ────────────────────────────────────────────────────────
-- Sem tenant_id aqui: itens só existem no contexto de uma venda,
-- que já carrega o tenant_id. Joins sempre passam pela tabela sales.
CREATE TABLE sale_items (
    id         UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    sale_id    UUID           NOT NULL REFERENCES sales(id) ON DELETE CASCADE,
    product_id UUID           NOT NULL REFERENCES products(id),
    quantity   INTEGER        NOT NULL,
    unit_price NUMERIC(15, 2) NOT NULL
);

-- ── Índices de performance ────────────────────────────────────────────────
-- Toda query analítica filtra por tenant_id + sale_date.
-- Sem esses índices, full table scan em tabelas grandes.
CREATE INDEX idx_sales_tenant_date    ON sales(tenant_id, sale_date);
CREATE INDEX idx_products_tenant      ON products(tenant_id);
CREATE INDEX idx_products_category    ON products(category_id);
CREATE INDEX idx_sale_items_sale      ON sale_items(sale_id);
CREATE INDEX idx_sale_items_product   ON sale_items(product_id);
CREATE INDEX idx_categories_tenant    ON categories(tenant_id);
CREATE INDEX idx_users_tenant         ON users(tenant_id);
CREATE INDEX idx_users_email          ON users(email);
