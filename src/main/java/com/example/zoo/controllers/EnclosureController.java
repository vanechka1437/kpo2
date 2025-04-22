package com.example.zoo.controllers;

import com.example.zoo.domain.Enclosure;
import com.example.zoo.dto.EnclosureRequest;
import com.example.zoo.infrastructure.repositories.EnclosureRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/enclosures")
public class EnclosureController {
    private final EnclosureRepository enclosureRepository;

    public EnclosureController(EnclosureRepository enclosureRepository) {
        this.enclosureRepository = enclosureRepository;
    }

    @PostMapping
    public ResponseEntity<Enclosure> createEnclosure(@RequestBody EnclosureRequest request) {
        Enclosure enclosure = new Enclosure(
                request.type(),
                request.maxCapacity()
        );
        enclosureRepository.save(enclosure);
        return ResponseEntity.status(HttpStatus.CREATED).body(enclosure);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enclosure> getEnclosure(@PathVariable UUID id) {
        Enclosure enclosure = enclosureRepository.findById(id);
        return enclosure != null
                ? ResponseEntity.ok(enclosure)
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Enclosure> getAllEnclosures() {
        return enclosureRepository.findAll();
    }
}