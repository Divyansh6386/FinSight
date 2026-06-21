CREATE TABLE transactions (

    id BIGSERIAL PRIMARY KEY,

    stock_id BIGINT NOT NULL,

    type VARCHAR(20) NOT NULL,

    quantity INTEGER NOT NULL,

    price NUMERIC(19,2) NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP,

    CONSTRAINT fk_transaction_stock
        FOREIGN KEY (stock_id)
        REFERENCES stocks(id)
        ON DELETE CASCADE
);