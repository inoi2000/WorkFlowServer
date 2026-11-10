package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.petproject.workflow.store.entities.StorageEventActionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private StorageEventActionType actionType;

    @NotNull
    private double volume;

    @NotNull
    private StorageDto storage;

    @NotNull
    private ProductDto product;

    @NotNull
    @JsonProperty("employee_id")
    private UUID employeeId;

    @NotNull
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime createdAt;
}
