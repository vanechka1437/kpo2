package com.example.zoo.domain.events;

import com.example.zoo.domain.FeedingSchedule;
import lombok.Getter;

/**
 * Событие, возникающее при наступлении времени кормления животного.
 * Генерируется при отметке кормления как выполненного.
 */
public class FeedingTimeEvent {
    @Getter
    private final FeedingSchedule schedule;
    private final boolean isCompleted;

    public FeedingTimeEvent(FeedingSchedule schedule) {
        if (schedule == null) {
            throw new IllegalArgumentException("Feeding schedule cannot be null");
        }
        this.schedule = schedule;
        this.isCompleted = schedule.isCompleted();
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public String toString() {
        return String.format(
                "FeedingTimeEvent[%s for %s at %s]",
                isCompleted ? "COMPLETED" : "PENDING",
                schedule.getAnimal().getName(),
                schedule.getFeedingTime()
        );
    }
}