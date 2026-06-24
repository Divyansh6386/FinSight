CREATE TABLE stocks (

    id BIGSERIAL PRIMARY KEY,

    symbol VARCHAR(20) NOT NULL,

    company_name VARCHAR(255) NOT NULL,

    quantity INTEGER NOT NULL,

    average_buy_price NUMERIC(19,2) NOT NULL,

    portfolio_id BIGINT NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP,

    CONSTRAINT fk_stock_portfolio
        FOREIGN KEY (portfolio_id)
        REFERENCES portfolios(id)
        ON DELETE CASCADE

);