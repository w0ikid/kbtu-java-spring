CREATE TABLE borrow_records (
    id          UUID PRIMARY KEY,
    book_id     UUID         NOT NULL,
    member_id   UUID         NOT NULL,
    borrowed_at TIMESTAMP    NOT NULL,
    returned_at TIMESTAMP,
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books(id)
);