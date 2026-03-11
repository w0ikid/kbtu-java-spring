package com.library.member_service.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record MemberResponse(
    UUID id,
    String name,
    String email,
    String phone,
    int borrowedBooksCount,
    LocalDateTime createdAt
) {}
