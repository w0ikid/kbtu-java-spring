CREATE TABLE notifications (
    id          UUID PRIMARY KEY,
    member_id   UUID         NOT NULL,
    recipient   VARCHAR(255) NOT NULL,
    subject     VARCHAR(255) NOT NULL,
    content     TEXT         NOT NULL,
    type        VARCHAR(50)  NOT NULL,
    sent_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
