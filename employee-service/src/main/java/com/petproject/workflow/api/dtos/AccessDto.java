package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.petproject.workflow.store.entities.AccessData;
import com.petproject.workflow.store.entities.AccessDurationType;
import com.petproject.workflow.store.entities.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessDto {

    @NotNull
    private UUID id;

    @NotNull
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @NotNull
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private AccessDurationType type;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Status status;

    @NotNull
    @JsonProperty("issuer_id")
    private UUID issuerId;

    @NotNull
    @JsonProperty("holder_id")
    private UUID holderId;

    @NotNull
    private AccessData data;

    @JsonProperty("valid_until")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate validUntil;
}
