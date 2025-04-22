package com.example.zoo.infrastructure.repositories;

import com.example.zoo.domain.FeedingSchedule;
import java.util.*;

public class InMemoryFeedingScheduleRepository implements FeedingScheduleRepository {
    private final Map<UUID, FeedingSchedule> storage = new HashMap<>();

    @Override
    public FeedingSchedule findById(UUID id) {
        return storage.get(id);
    }

    @Override
    public void save(FeedingSchedule schedule) {
        storage.put(schedule.getId(), schedule);
    }

    @Override
    public List<FeedingSchedule> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void delete(UUID id) {
        storage.remove(id);
    }
}