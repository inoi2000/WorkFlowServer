package com.petproject.workflow.api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskNotificationDto {

    @NotNull
    private UUID taskId;

    @NotNull
    private UUID consumer;

    @NotNull
    private String message;
}