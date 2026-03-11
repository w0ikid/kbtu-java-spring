package com.library.book_service.api.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;

public record CancelBorrowRequest(
    @NotNull(message = "Member ID is required")
    UUID memberId
) {}
