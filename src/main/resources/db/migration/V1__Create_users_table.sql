CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY,

    full_name VARCHAR(100) NOT NULL,

    email VARCHAR(150) NOT NULL UNIQUE,

    password VARCHAR(255) NOT NULL,

    role VARCHAR(30) NOT NULL,

    enabled BOOLEAN NOT NULL DEFAULT TRUE,

    account_locked BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_user_email
ON users(email);