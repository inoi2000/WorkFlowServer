package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.petproject.workflow.store.entities.CarStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {

    @NotNull
    private UUID id;

    @NotNull
    private String brand;

    @NotNull
    private String model;

    @NotNull
    @JsonProperty("license_plate")
    private String licensePlate;

    @NotNull
    private String vin;

    @NotNull
    private int year;

    @NotNull
    private String color;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private CarStatus status;
}
