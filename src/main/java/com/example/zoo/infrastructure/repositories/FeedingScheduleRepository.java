package com.example.zoo.infrastructure.repositories;

import com.example.zoo.domain.FeedingSchedule;
import java.util.List;
import java.util.UUID;

/**
 * Интерфейс репозитория для управления расписанием кормлений
 */
public interface FeedingScheduleRepository {
    FeedingSchedule findById(UUID id);
    void save(FeedingSchedule schedule);
    List<FeedingSchedule> findAll();
    void delete(UUID id);
}