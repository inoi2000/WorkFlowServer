package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrailerDto {

    @NotNull
    private UUID id;

    @NotNull
    private String brand;

    @NotNull
    @JsonProperty("license_plate")
    private String licensePlate;

    @NotNull
    @JsonProperty("volume_liter")
    private double volumeLiter;

    @NotNull
    private String material;
}
