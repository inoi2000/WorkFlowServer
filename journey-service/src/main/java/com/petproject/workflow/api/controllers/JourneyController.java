package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.JourneyDto;
import com.petproject.workflow.api.services.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/journeys", produces = "application/json")
public class JourneyController {

    private final JourneyService journeyService;

    @Autowired
    public JourneyController(JourneyService journeyService) {
        this.journeyService = journeyService;
    }

    @GetMapping("/")
    public Iterable<JourneyDto> getAllJourneys() {
        return journeyService.getAllJourneys();
    }
}