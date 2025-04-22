// Domain Layer - Events
// =====================

package com.example.zoo.domain.events;

import com.example.zoo.domain.Animal;
import com.example.zoo.domain.Enclosure;
import lombok.Getter;


@Getter
public class AnimalMovedEvent {
    private final Animal animal;
    private final Enclosure previousEnclosure;
    private final Enclosure newEnclosure;

    public AnimalMovedEvent(Animal animal, Enclosure newEnclosure) {
        this(animal, animal.getEnclosure(), newEnclosure);
    }

    public AnimalMovedEvent(Animal animal, Enclosure previousEnclosure, Enclosure newEnclosure) {
        if (animal == null) {
            throw new IllegalArgumentException("Animal cannot be null");
        }
        this.animal = animal;
        this.previousEnclosure = previousEnclosure;
        this.newEnclosure = newEnclosure;
    }

    @Override
    public String toString() {
        return String.format(
                "AnimalMovedEvent[%s from %s to %s]",
                animal.getName(),
                previousEnclosure != null ? previousEnclosure.getId() : "null",
                newEnclosure != null ? newEnclosure.getId() : "null"
        );
    }
}