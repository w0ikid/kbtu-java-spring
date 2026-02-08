package com.example.demo.user.api.dto;

public record UserCreateRequest(
    String username,
    String email,
    String password
) {}