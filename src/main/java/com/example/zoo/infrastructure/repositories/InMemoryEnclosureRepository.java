package com.example.zoo.infrastructure.repositories;

import com.example.zoo.domain.Enclosure;
import java.util.*;

public class InMemoryEnclosureRepository implements EnclosureRepository {
    private final Map<UUID, Enclosure> storage = new HashMap<>();

    @Override
    public Enclosure findById(UUID id) {
        return storage.get(id);
    }

    @Override
    public void save(Enclosure enclosure) {
        storage.put(enclosure.getId(), enclosure);
    }

    @Override
    public List<Enclosure> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void delete(UUID id) {
        storage.remove(id);
    }
}