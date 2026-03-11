CREATE TABLE books (
    id          UUID PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    author      VARCHAR(255) NOT NULL,
    author_email VARCHAR(255) NOT NULL,
    price       DECIMAL(10,2) NOT NULL,
    status      VARCHAR(50)  NOT NULL DEFAULT 'AVAILABLE',
    created_at  TIMESTAMP    NOT NULL
);