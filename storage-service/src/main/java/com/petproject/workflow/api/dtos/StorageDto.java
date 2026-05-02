package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageDto {

    @NotNull
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    @JsonProperty("total_volume")
    private double totalVolume;

    @NotNull
    @JsonProperty("current_volume")
    private double currentVolume;

    @JsonProperty("current_waste")
    private WasteDto currentWaste;

    @JsonProperty("allowed_wastes")
    private List<WasteDto> allowedWastes = new ArrayList<>();
}