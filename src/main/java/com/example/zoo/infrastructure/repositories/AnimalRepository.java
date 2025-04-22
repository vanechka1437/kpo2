package com.example.zoo.infrastructure.repositories;

import com.example.zoo.domain.Animal;
import java.util.List;
import java.util.UUID;

/**
 * Интерфейс репозитория для работы с животными
 */
public interface AnimalRepository {
    Animal findById(UUID id);
    void save(Animal animal);
    List<Animal> findAll();
    void delete(UUID id);
}