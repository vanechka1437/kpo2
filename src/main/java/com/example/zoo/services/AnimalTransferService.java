package com.example.zoo.services;

import com.example.zoo.domain.Animal;
import com.example.zoo.domain.Enclosure;
import com.example.zoo.infrastructure.repositories.AnimalRepository;
import com.example.zoo.infrastructure.repositories.EnclosureRepository;
import java.util.UUID;

/**
 * Сервис для перемещения животных между вольерами.
 * Реализует бизнес-логику проверок и уведомлений.
 */
public class AnimalTransferService {
    private final AnimalRepository animalRepository;
    private final EnclosureRepository enclosureRepository;

    public AnimalTransferService(
            AnimalRepository animalRepository,
            EnclosureRepository enclosureRepository
    ) {
        this.animalRepository = animalRepository;
        this.enclosureRepository = enclosureRepository;
    }

    /**
     * Перемещает животное в указанный вольер
     * @param animalId ID животного
     * @param newEnclosureId ID целевого вольера
     * @throws IllegalArgumentException если животное или вольер не найдены
     * @throws IllegalStateException если перемещение невозможно по бизнес-правилам
     */
    public void transferAnimal(UUID animalId, UUID newEnclosureId) {
        Animal animal = animalRepository.findById(animalId);
        if (animal == null) {
            throw new IllegalArgumentException("Animal not found: " + animalId);
        }

        Enclosure newEnclosure = enclosureRepository.findById(newEnclosureId);
        if (newEnclosure == null) {
            throw new IllegalArgumentException("Enclosure not found: " + newEnclosureId);
        }

        animal.moveToEnclosure(newEnclosure);

        animalRepository.save(animal);
        enclosureRepository.save(newEnclosure);
    }
}