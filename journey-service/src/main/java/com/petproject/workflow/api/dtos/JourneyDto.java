package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.petproject.workflow.api.serialization.StatementSerializer;
import com.petproject.workflow.store.entities.JourneyStatus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JourneyDto {

    @NotNull
    private UUID id;

    @NotNull
    private CarDto car;

    @NotNull
    @JsonSerialize(using = StatementSerializer.class)
    private StatementDto statement;

    @NotNull
    private EmployeeDto driver;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private JourneyStatus status;

    @JsonProperty("start_odometer")
    private double startOdometer;

    @JsonProperty("end_odometer")
    private double endOdometer;

    @NotNull
    @JsonProperty("estimated_duration_minutes")
    private int estimatedDurationMinutes;

    @NotNull
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("confirmed_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime confirmedAt;

    @JsonProperty("started_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startedAt;

    @JsonProperty("finished_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime finishedAt;

    @JsonProperty("canceled_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime canceledAt;
}
