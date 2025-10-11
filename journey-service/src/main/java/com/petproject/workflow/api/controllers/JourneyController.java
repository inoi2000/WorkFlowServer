package com.petproject.workflow.api.controllers;

import com.petproject.workflow.store.entities.Journey;
import com.petproject.workflow.store.repositories.JourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/journeys", produces = "application/json")
public class JourneyController {

    private final JourneyRepository journeyRepository;

    @Autowired
    public JourneyController(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    @GetMapping("/")
    public Iterable<Journey> getAllJourneys() {
        return journeyRepository.findAll();
    }
}
