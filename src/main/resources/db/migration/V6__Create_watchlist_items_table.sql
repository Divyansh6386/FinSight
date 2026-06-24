CREATE TABLE watchlist_items (

    id BIGSERIAL PRIMARY KEY,

    symbol VARCHAR(20) NOT NULL,

    company_name VARCHAR(150) NOT NULL,

    watchlist_id BIGINT NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_watchlist_item_watchlist
        FOREIGN KEY (watchlist_id)
        REFERENCES watchlists(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_watchlist_symbol
        UNIQUE (watchlist_id, symbol)
);

CREATE INDEX idx_watchlist_item_watchlist
ON watchlist_items(watchlist_id);