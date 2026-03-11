package com.library.book_service.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.library.book_service.domain.BookStatus;

public record BookResponse(
    UUID id,
    String title,
    String author,
    String authorEmail,
    BigDecimal price,
    String description,
    BookStatus status,
    LocalDateTime createdAt
) {}