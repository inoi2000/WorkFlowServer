package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.petproject.workflow.store.AbsenceType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbsencePolicyDto {

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
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime createdAt;

    @NotNull
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime updatedAt;
}
