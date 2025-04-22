package com.example.zoo.infrastructure.repositories;

import com.example.zoo.domain.Enclosure;
import java.util.List;
import java.util.UUID;

/**
 * Интерфейс репозитория для управления вольерами
 */
public interface EnclosureRepository {
    Enclosure findById(UUID id);
    void save(Enclosure enclosure);
    List<Enclosure> findAll();
    void delete(UUID id);
}