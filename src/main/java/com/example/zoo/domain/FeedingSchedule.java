package com.example.zoo.domain;

import com.example.zoo.domain.events.FeedingTimeEvent;
import com.example.zoo.infrastructure.DomainEventPublisher;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

public class FeedingSchedule {
    @Getter
    private final UUID id;
    @Getter
    private final Animal animal;
    @Getter
    private LocalDateTime feedingTime;
    @Getter
    private FoodType foodType;
    private boolean isCompleted;

    public FeedingSchedule(Animal animal, LocalDateTime feedingTime, FoodType foodType) {
        if (animal == null) {
            throw new IllegalArgumentException("Animal cannot be null");
        }
        if (feedingTime == null) {
            throw new IllegalArgumentException("Feeding time cannot be null");
        }
        if (foodType == null) {
            throw new IllegalArgumentException("Food type cannot be null");
        }

        this.id = UUID.randomUUID();
        this.animal = animal;
        this.feedingTime = feedingTime;
        this.foodType = foodType;
        this.isCompleted = false;
    }

    public void reschedule(LocalDateTime newTime, FoodType newFood) {
        if (newTime == null || newFood == null) {
            throw new IllegalArgumentException("Invalid reschedule parameters");
        }
        this.feedingTime = newTime;
        this.foodType = newFood;
        this.isCompleted = false; // Сброс статуса при изменении
    }

    public void markAsCompleted() {
        if (!isCompleted) {
            this.isCompleted = true;
            DomainEventPublisher.publish(new FeedingTimeEvent(this));
        }
    }

    public boolean isCompleted() { return isCompleted; }

    @Override
    public String toString() {
        return String.format(
                "FeedingSchedule[%s for %s at %s]",
                foodType.name(),
                animal.getName(),
                feedingTime.toString()
        );
    }
}