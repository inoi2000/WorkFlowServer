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
public class ApprovalDto {

    @NotNull
    private UUID id;

    @NotNull
    @JsonProperty("absence")
    private AbsenceDto absence;

    @NotNull
    @JsonProperty("approver")
    private EmployeeDto approver;

    @JsonProperty("description")
    private String description;
}