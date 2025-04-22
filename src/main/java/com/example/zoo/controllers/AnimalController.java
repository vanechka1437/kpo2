package com.example.zoo.controllers;

import com.example.zoo.dto.AnimalRequest;
import com.example.zoo.domain.FoodType;
import com.example.zoo.domain.Animal;
import com.example.zoo.infrastructure.repositories.AnimalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {
    private final AnimalRepository animalRepository;

    public AnimalController(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @PostMapping
    public ResponseEntity<Animal> createAnimal(@RequestBody AnimalRequest request) {
        Animal animal = new Animal(
                request.species(),
                request.name(),
                request.birthDate(),
                request.gender(),
                new FoodType(request.favoriteFood())
        );
        animalRepository.save(animal);
        return ResponseEntity.status(HttpStatus.CREATED).body(animal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimal(@PathVariable UUID id) {
        Animal animal = animalRepository.findById(id);
        return animal != null
                ? ResponseEntity.ok(animal)
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable UUID id) {
        if (animalRepository.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        animalRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}