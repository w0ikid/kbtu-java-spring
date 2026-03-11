package com.library.book_service.infra;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookBorrowedEvent(
    UUID bookId,
    String bookTitle,
    UUID memberId,
    LocalDateTime borrowedAt
) {}