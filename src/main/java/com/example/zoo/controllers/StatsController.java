package com.example.zoo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.zoo.services.ZooStatisticsService;

import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {
    private final ZooStatisticsService statsService;

    public StatsController(ZooStatisticsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping
    public Map<String, Integer> getStatistics() {
        return statsService.getStatistics();
    }
}