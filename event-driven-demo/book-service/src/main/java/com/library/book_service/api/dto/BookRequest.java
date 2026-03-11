package com.library.book_service.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record BookRequest(
    @NotBlank String title,
    @NotBlank String author,
    @NotBlank @Email String authorEmail,
    @NotNull @Min(0) BigDecimal price,
    String description
) {}