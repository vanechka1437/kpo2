package com.example.zoo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.zoo.services.AnimalTransferService;

import java.util.UUID;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    private final AnimalTransferService transferService;

    public TransferController(AnimalTransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<String> transferAnimal(
            @RequestParam UUID animalId,
            @RequestParam UUID enclosureId
    ) {
        try {
            transferService.transferAnimal(animalId, enclosureId);
            return ResponseEntity.ok("Animal transferred");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}