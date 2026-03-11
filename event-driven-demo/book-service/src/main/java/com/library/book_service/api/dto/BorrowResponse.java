package com.library.book_service.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record BorrowResponse(
    UUID id,
    UUID bookId,
    UUID memberId,
    LocalDateTime borrowedAt
) {}