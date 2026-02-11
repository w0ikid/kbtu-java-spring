package com.example.demo.user.api.dto;

public record LoginRequest(
    String email,
    String password
) {}