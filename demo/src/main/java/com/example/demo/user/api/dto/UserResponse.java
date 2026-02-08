package com.example.demo.user.api.dto;

import java.time.OffsetDateTime;

public record UserResponse(
    Long id,
    String username,
    String email,
    OffsetDateTime createdAt
) {}
