package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class StatementDto {

    @NotNull
    private UUID id;

    @NotNull
    @JsonProperty("logist")
    private EmployeeDto logist;

    @NotNull
    private String data;

    @NotNull
    @JsonProperty("contact_phone")
    private String contactPhone;

    @NotNull
    @JsonProperty("destination_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime destinationTime;

    @NotNull
    @JsonProperty("destination_address")
    private String destinationAddress;

    @NotNull
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @NotNull
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    private JourneyDto journey;
}
