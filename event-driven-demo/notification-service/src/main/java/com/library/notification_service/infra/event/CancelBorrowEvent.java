package com.library.notification_service.infra.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record CancelBorrowEvent(
    UUID bookId,
    String bookTitle,
    UUID memberId,
    LocalDateTime cancelledAt
) {}
