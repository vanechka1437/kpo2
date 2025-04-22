package com.example.zoo.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record FeedingRequest(
        UUID animalId,
        LocalDateTime time,
        String foodType
) {}