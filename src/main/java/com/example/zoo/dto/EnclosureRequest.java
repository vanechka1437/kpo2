package com.example.zoo.dto;
import com.example.zoo.domain.enums.EnclosureType;

public record EnclosureRequest(
        EnclosureType type,
        int maxCapacity
) {}