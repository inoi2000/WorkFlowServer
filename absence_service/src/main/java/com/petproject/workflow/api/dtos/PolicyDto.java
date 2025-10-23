package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.petproject.workflow.store.AbsenceType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyDto {

    @NotNull
    private UUID id;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private AbsenceType type;

    @NotNull
    @JsonProperty("max_duration_days")
    private Integer maxDurationDays;

    @NotNull
    @JsonProperty("requires_approval")
    private Boolean requiresApproval;

    @NotNull
    @JsonProperty("can_approve_position_ids")
    private List<UUID> canApprovePositionIds;
}