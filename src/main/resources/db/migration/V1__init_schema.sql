CREATE TABLE companies (
    id       UUID PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    document VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE users (
    id         UUID PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(50)  NOT NULL,
    company_id UUID NOT NULL REFERENCES companies(id)
);

CREATE TABLE categories (
    id         UUID PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    company_id UUID NOT NULL
);

CREATE TABLE products (
    id          UUID    PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    price       DOUBLE PRECISION NOT NULL,
    active      BOOLEAN        NOT NULL DEFAULT TRUE,
    category_id UUID NOT NULL REFERENCES categories(id),
    company_id  UUID NOT NULL
);

CREATE TABLE sales (
    id         UUID PRIMARY KEY,
    date       TIMESTAMP NOT NULL,
    company_id UUID NOT NULL
);

CREATE TABLE sale_items (
    id         UUID PRIMARY KEY,
    sale_id    UUID           NOT NULL REFERENCES sales(id) ON DELETE CASCADE,
    product_id UUID           NOT NULL,
    quantity   INTEGER        NOT NULL,
    unit_price NUMERIC(15,2)  NOT NULL,
    name       VARCHAR(255)   NOT NULL,
    company_id UUID           NOT NULL
);

CREATE INDEX idx_users_company      ON users(company_id);
CREATE INDEX idx_categories_company ON categories(company_id);
CREATE INDEX idx_products_company   ON products(company_id);
CREATE INDEX idx_sales_company_date ON sales(company_id, date);
CREATE INDEX idx_sale_items_sale    ON sale_items(sale_id);