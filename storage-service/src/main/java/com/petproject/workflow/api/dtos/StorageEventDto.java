package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.petproject.workflow.store.entities.ActionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageEventDto {

    @NotNull
    private UUID id;

    @NotNull
    @JsonProperty("action_type")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ActionType actionType;

    @NotNull
    private double volume;

    @NotNull
    private StorageDto storage;

    @NotNull
    private WasteDto waste;
}
