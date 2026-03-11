package com.library.member_service.infra.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookBorrowedEvent(
    UUID bookId,
    String bookTitle,
    UUID memberId,
    LocalDateTime borrowedAt
) {}
