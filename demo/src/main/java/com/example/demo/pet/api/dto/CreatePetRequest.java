package com.example.demo.pet.api.dto;

public record CreatePetRequest(
    String name,
    String type
) {}
