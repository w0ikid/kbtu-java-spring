package com.example.demo.user.api.dto;

public record LoginResponse(
    String token,
    String type,
    Long userId,
    String email,
    String username
) {
    public LoginResponse(String token, Long userId, String email, String username) {
        this("Bearer", token, userId, email, username);
    }
}