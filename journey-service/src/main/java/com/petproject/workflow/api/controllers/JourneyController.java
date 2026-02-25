package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.DateTimeUpdateDto;
import com.petproject.workflow.api.dtos.JourneyDto;
import com.petproject.workflow.api.exceptions.NotFoundIdException;
import com.petproject.workflow.api.services.JourneyService;
import com.petproject.workflow.store.entities.JourneyStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //  подтвирждение уведомления
    @PostMapping("/confirm")
    public ResponseEntity<JourneyDto> confirmJourney(@RequestBody @Valid DateTimeUpdateDto dateTimeUpdateDto) {
        try {
            return journeyService.changeJourneyStatus(JourneyStatus.CONFIRMED, dateTimeUpdateDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //  начало выезда
    @PostMapping("/start")
    public ResponseEntity<JourneyDto> startJourney(@RequestBody @Valid DateTimeUpdateDto dateTimeUpdateDto) {
        try {
            return journeyService.changeJourneyStatus(JourneyStatus.STARTED, dateTimeUpdateDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //  окончание выезда
    @PostMapping("/finish")
    public ResponseEntity<JourneyDto> finishJourney(@RequestBody @Valid DateTimeUpdateDto dateTimeUpdateDto) {
        try {
            return journeyService.changeJourneyStatus(JourneyStatus.FINISHED, dateTimeUpdateDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //  окончание выезда
    @PostMapping("/cancel")
    public ResponseEntity<JourneyDto> cancelJourney(@RequestBody @Valid DateTimeUpdateDto dateTimeUpdateDto) {
        try {
            return journeyService.changeJourneyStatus(JourneyStatus.CANCELED, dateTimeUpdateDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}