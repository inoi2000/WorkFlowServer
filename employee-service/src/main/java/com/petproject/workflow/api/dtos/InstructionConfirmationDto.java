package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructionConfirmationDto {
    @NotNull
    @JsonProperty("employee_id")
    private UUID employeeId;
    @JsonProperty("is_confirmed")
    private boolean isConfirmed;
    @JsonProperty("confirmed_at")
    private LocalDateTime confirmedAt;
}
