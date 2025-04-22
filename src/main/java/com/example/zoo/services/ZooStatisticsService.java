package com.example.zoo.services;

import com.example.zoo.infrastructure.repositories.AnimalRepository;
import com.example.zoo.infrastructure.repositories.EnclosureRepository;
import java.util.HashMap;
import java.util.Map;

/**
 * Сервис для сбора статистики по зоопарку
 */
public class ZooStatisticsService {
    private final AnimalRepository animalRepository;
    private final EnclosureRepository enclosureRepository;

    public ZooStatisticsService(
            AnimalRepository animalRepository,
            EnclosureRepository enclosureRepository
    ) {
        this.animalRepository = animalRepository;
        this.enclosureRepository = enclosureRepository;
    }

    /**
     * Собирает ключевые метрики зоопарка
     * @return Map с ключами:
     *   - totalAnimals: общее количество животных
     *   - totalEnclosures: общее количество вольеров
     *   - freeEnclosures: количество пустых вольеров
     *   - avgAnimalsPerEnclosure: среднее количество животных на вольер
     */
    public Map<String, Integer> getStatistics() {
        Map<String, Integer> stats = new HashMap<>();

        int totalAnimals = animalRepository.findAll().size();
        int totalEnclosures = enclosureRepository.findAll().size();
        int occupiedEnclosures = (int) enclosureRepository.findAll().stream()
                .filter(e -> !e.getAnimals().isEmpty())
                .count();

        stats.put("totalAnimals", totalAnimals);
        stats.put("totalEnclosures", totalEnclosures);
        stats.put("freeEnclosures", totalEnclosures - occupiedEnclosures);
        stats.put("avgAnimalsPerEnclosure",
                totalEnclosures > 0 ? totalAnimals / totalEnclosures : 0);

        return stats;
    }
}