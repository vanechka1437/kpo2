package com.example.zoo.infrastructure;

import com.example.zoo.infrastructure.repositories.*;
import com.example.zoo.services.AnimalTransferService;
import com.example.zoo.services.FeedingOrganizationService;
import com.example.zoo.services.ZooStatisticsService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // Репозитории
    @Bean
    public AnimalRepository animalRepository() {
        return new InMemoryAnimalRepository();
    }

    @Bean
    public EnclosureRepository enclosureRepository() {
        return new InMemoryEnclosureRepository();
    }

    @Bean
    public FeedingScheduleRepository feedingScheduleRepository() {
        return new InMemoryFeedingScheduleRepository();
    }

    // Сервисы
    @Bean
    public AnimalTransferService animalTransferService(
            AnimalRepository animalRepository,
            EnclosureRepository enclosureRepository
    ) {
        return new AnimalTransferService(animalRepository, enclosureRepository);
    }

    @Bean
    public FeedingOrganizationService feedingOrganizationService(
            FeedingScheduleRepository scheduleRepository,
            AnimalRepository animalRepository
    ) {
        return new FeedingOrganizationService(scheduleRepository, animalRepository);
    }

    @Bean
    public ZooStatisticsService zooStatisticsService(
            AnimalRepository animalRepository,
            EnclosureRepository enclosureRepository
    ) {
        return new ZooStatisticsService(animalRepository, enclosureRepository);
    }

    // Интеграция доменных событий с Spring
    @Bean
    public DomainEventPublisher domainEventPublisher(ApplicationEventPublisher publisher) {
        return new DomainEventPublisher(publisher);
    }
}