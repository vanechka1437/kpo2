package com.example.zoo.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

public class DomainEventPublisher {
    private static ApplicationEventPublisher springPublisher;

    @Autowired
    public DomainEventPublisher(ApplicationEventPublisher publisher) {
        springPublisher = publisher;
    }

    public static void publish(Object event) {
        springPublisher.publishEvent(event);
    }
}