package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.CarDto;
import com.petproject.workflow.api.dtos.CarMapper;
import com.petproject.workflow.api.exceptions.NotFoundIdException;
import com.petproject.workflow.store.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/cars", produces = "application/json")
@RequiredArgsConstructor
public class CarController {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @GetMapping("/")
    public Iterable<CarDto> getAllCars() {
        return carRepository.findAll()
                .stream().map(carMapper::mapToCarDto).toList();
    }

    @GetMapping("/{carId}")
    public CarDto getCarById(@PathVariable UUID carId) throws NotFoundIdException {
        return carRepository.getCarById(carId)
                .map(carMapper::mapToCarDto)
                .orElseThrow(() -> new NotFoundIdException("Journey with id " + carId + " not found"));
    }
}
