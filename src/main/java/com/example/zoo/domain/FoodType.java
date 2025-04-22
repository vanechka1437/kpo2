package com.example.zoo.domain;

public record FoodType(String name) {
    public FoodType {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Food type cannot be empty");
        }
    }
}