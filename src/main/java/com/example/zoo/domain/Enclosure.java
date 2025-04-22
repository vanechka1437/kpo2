package com.example.zoo.domain;

import com.example.zoo.domain.enums.EnclosureType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Enclosure {
    @Getter
    private final UUID id;
    @Getter
    private final EnclosureType type;
    @Getter
    private final int maxCapacity;
    private final List<Animal> animals = new ArrayList<>();

    public Enclosure(EnclosureType type, int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.id = UUID.randomUUID();
        this.type = type;
        this.maxCapacity = maxCapacity;
    }

    public void addAnimal(Animal animal) {
        if (animals.size() >= maxCapacity) {
            throw new IllegalStateException("Enclosure " + id + " is full");
        }
        if (animals.contains(animal)) {
            throw new IllegalArgumentException("Animal already in enclosure");
        }
        animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        if (!animals.remove(animal)) {
            throw new IllegalArgumentException("Animal not found in enclosure");
        }
    }

    public void clean() {
        System.out.println("Enclosure " + id + " (" + type + ") cleaned");
    }

    public boolean isCompatibleWith(String species) {
        return this.type.name().equalsIgnoreCase(species);
    }

    public boolean hasSpace() {
        return animals.size() < maxCapacity;
    }

    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

    public int getCurrentOccupancy() { return animals.size(); }
}