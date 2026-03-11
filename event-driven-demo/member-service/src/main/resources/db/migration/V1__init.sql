CREATE TABLE members (
    id                   UUID PRIMARY KEY,
    name                 VARCHAR(255) NOT NULL,
    email                VARCHAR(255) NOT NULL UNIQUE,
    phone                VARCHAR(50),
    borrowed_books_count INT          NOT NULL DEFAULT 0,
    created_at           TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE member_borrow_history (
    id          UUID PRIMARY KEY,
    member_id   UUID         NOT NULL,
    book_id     UUID         NOT NULL,
    book_title  VARCHAR(255) NOT NULL,
    borrowed_at TIMESTAMP    NOT NULL,
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES members(id)
);
