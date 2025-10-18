package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.FuellingDto;
import com.petproject.workflow.api.exceptions.NotFoundIdException;
import com.petproject.workflow.api.services.FuellingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/fuellings", produces = "application/json")
@RequiredArgsConstructor
public class FuellingController {

    private final FuellingService fuellingService;

    @GetMapping("/")
    public List<FuellingDto> getAllFuellings() {
        return fuellingService.getAllFuellings();
    }

    @GetMapping("/{fuellingId}")
    public FuellingDto getFuellingById(@PathVariable UUID fuellingId) throws NotFoundIdException {
        return fuellingService.getFuellingById(fuellingId);
    }
}
