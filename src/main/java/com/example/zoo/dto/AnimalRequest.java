package com.example.zoo.dto;

import java.time.LocalDate;

public record AnimalRequest(
        String species,
        String name,
        LocalDate birthDate,
        String gender,
        String favoriteFood
) {}