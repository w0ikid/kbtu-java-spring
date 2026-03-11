package com.library.book_service.api.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record BorrowRequest(
    @NotNull UUID memberId
) {}
