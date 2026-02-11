package com.example.demo.pet.api.dto;

public record CreatePetResponce(
    Long id,
    String name,
    String type,
    Long ownerId
) {}
