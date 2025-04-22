package com.example.zoo.services;

import com.example.zoo.domain.Animal;
import com.example.zoo.domain.FeedingSchedule;
import com.example.zoo.domain.FoodType;
import com.example.zoo.infrastructure.repositories.AnimalRepository;
import com.example.zoo.infrastructure.repositories.FeedingScheduleRepository;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сервис для управления расписанием кормлений животных.
 * Инкапсулирует бизнес-правила организации процесса кормления.
 */
public class FeedingOrganizationService {
    private final FeedingScheduleRepository scheduleRepository;
    private final AnimalRepository animalRepository;

    public FeedingOrganizationService(
            FeedingScheduleRepository scheduleRepository,
            AnimalRepository animalRepository
    ) {
        this.scheduleRepository = scheduleRepository;
        this.animalRepository = animalRepository;
    }

    /**
     * Создаёт новое расписание кормления с проверкой бизнес-правил
     */
    public void scheduleFeeding(UUID animalId, LocalDateTime time, FoodType food) {
        Animal animal = animalRepository.findById(animalId);
        if (animal == null) {
            throw new IllegalArgumentException("Animal not found: " + animalId);
        }

        validateFoodPreferences(animal, food);
        checkTimeConflicts(animal, time);

        FeedingSchedule schedule = new FeedingSchedule(animal, time, food);
        scheduleRepository.save(schedule);
    }

    /**
     * Помечает кормление как выполненное
     */
    public void completeFeeding(UUID scheduleId) {
        FeedingSchedule schedule = scheduleRepository.findById(scheduleId);
        if (schedule == null) {
            throw new IllegalArgumentException("Schedule not found: " + scheduleId);
        }

        schedule.markAsCompleted();
        scheduleRepository.save(schedule);
    }

    private void validateFoodPreferences(Animal animal, FoodType food) {
        if (!animal.getFavoriteFood().equals(food)) {
            throw new IllegalArgumentException(
                    "Animal " + animal.getName() + " prefers " +
                            animal.getFavoriteFood().name()
            );
        }
    }

    private void checkTimeConflicts(Animal animal, LocalDateTime newTime) {
        scheduleRepository.findAll().stream()
                .filter(s -> s.getAnimal().equals(animal))
                .filter(s -> s.getFeedingTime().isAfter(newTime.minusHours(1)))
                .filter(s -> s.getFeedingTime().isBefore(newTime.plusHours(1)))
                .findAny()
                .ifPresent(conflict -> {
                    throw new IllegalStateException(
                            "Feeding time conflict for " + animal.getName() +
                                    " at " + conflict.getFeedingTime()
                    );
                });
    }
}