package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.JourneyDto;
import com.petproject.workflow.api.exceptions.NotFoundIdException;
import com.petproject.workflow.api.services.JourneyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/journeys", produces = "application/json")
@RequiredArgsConstructor
public class JourneyController {

    private final JourneyService journeyService;

    @GetMapping("/")
    public Iterable<JourneyDto> getAllJourneys() {
        return journeyService.getAllJourneys();
    }

    @GetMapping("/{journeyId}")
    public JourneyDto getJourneyById(@PathVariable UUID journeyId) throws NotFoundIdException {
        return journeyService.getJourneyById(journeyId);
    }

    @GetMapping("/drivers/{driverId}")
    public Iterable<JourneyDto> getJourneysByDriverId(@PathVariable UUID driverId) {
        return journeyService.getJourneysByDriverId(driverId);
    }

    @GetMapping("/cars/{carId}")
    public Iterable<JourneyDto> getJourneysByCarId(@PathVariable UUID carId) {
        return journeyService.getJourneysByCarId(carId);
    }
}