package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.petproject.workflow.store.entities.Employee;
import com.petproject.workflow.store.entities.InstructionData;
import com.petproject.workflow.store.entities.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructionDto {

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
    private Status status;

    @NotNull
    @JsonProperty("instructor_id")
    private UUID instructorId;

    @NotNull
    private InstructionData data;

    @JsonProperty("valid_until")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate validUntil;

    @JsonProperty("confirmation")
    private InstructionConfirmationDto instructionConfirmation;

    @JsonProperty("confirmations")
    private List<InstructionConfirmationDto> instructionConfirmations;
}
