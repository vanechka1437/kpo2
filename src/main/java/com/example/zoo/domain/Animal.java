package com.example.zoo.domain;

import com.example.zoo.domain.enums.HealthStatus;
import com.example.zoo.domain.events.AnimalMovedEvent;
import com.example.zoo.infrastructure.DomainEventPublisher;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

public class Animal {
    @Getter
    private final UUID id;
    @Getter
    private final String species;
    @Getter
    private final String name;
    private final FoodType favoriteFood;
    @Getter
    private HealthStatus healthStatus;
    @Getter
    private Enclosure enclosure;

    public Animal(
            String species,
            String name,
            LocalDate birthDate,
            String gender,
            FoodType favoriteFood
    ) {
        this.id = UUID.randomUUID();
        this.species = validateSpecies(species);
        this.name = name;
        this.favoriteFood = favoriteFood;
        this.healthStatus = HealthStatus.HEALTHY;
    }

    public void feed(FoodType food) {
        if (food.equals(this.favoriteFood)) {
            System.out.println(name + " happily eats " + food.name());
        } else {
            System.out.println(name + " refuses to eat " + food.name());
            this.healthStatus = HealthStatus.SICK;
        }
    }

    public void heal() {
        this.healthStatus = HealthStatus.HEALTHY;
        System.out.println(name + " has been treated");
    }

    public void moveToEnclosure(Enclosure newEnclosure) {
        if (!newEnclosure.isCompatibleWith(this.species)) {
            throw new IllegalArgumentException(
                    "Enclosure type " + newEnclosure.getType() +
                            " is incompatible with " + species
            );
        }

        if (this.enclosure != null) {
            this.enclosure.removeAnimal(this);
        }

        newEnclosure.addAnimal(this);
        this.enclosure = newEnclosure;
        DomainEventPublisher.publish(new AnimalMovedEvent(this, newEnclosure));
    }

    private String validateSpecies(String species) {
        if (!species.matches("[A-Za-z ]+")) {
            throw new IllegalArgumentException("Invalid species format");
        }
        return species;
    }

    public FoodType getFavoriteFood() {
        return favoriteFood;
    }


}