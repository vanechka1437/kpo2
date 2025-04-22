package com.example.zoo.controllers;

import com.example.zoo.domain.FoodType;
import com.example.zoo.dto.FeedingRequest;
import com.example.zoo.infrastructure.repositories.AnimalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.zoo.services.FeedingOrganizationService;

import java.util.UUID;

@RestController
@RequestMapping("/api/feeding")
public class FeedingController {
    private final FeedingOrganizationService feedingService;

    public FeedingController(
            FeedingOrganizationService feedingService,
            AnimalRepository animalRepository
    ) {
        this.feedingService = feedingService;
    }

    @PostMapping
    public ResponseEntity<String> scheduleFeeding(@RequestBody FeedingRequest request) {
        try {
            feedingService.scheduleFeeding(
                    request.animalId(),
                    request.time(),
                    new FoodType(request.foodType())
            );
            return ResponseEntity.ok("Feeding scheduled");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<String> completeFeeding(@PathVariable UUID id) {
        try {
            feedingService.completeFeeding(id);
            return ResponseEntity.ok("Feeding completed");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}