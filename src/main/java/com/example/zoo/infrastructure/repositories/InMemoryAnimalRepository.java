package com.example.zoo.infrastructure.repositories;

import com.example.zoo.domain.Animal;
import java.util.*;

public class InMemoryAnimalRepository implements AnimalRepository {
    private final Map<UUID, Animal> storage = new HashMap<>();

    @Override
    public Animal findById(UUID id) {
        return storage.get(id);
    }

    @Override
    public void save(Animal animal) {
        storage.put(animal.getId(), animal);
    }

    @Override
    public List<Animal> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void delete(UUID id) {
        storage.remove(id);
    }
}