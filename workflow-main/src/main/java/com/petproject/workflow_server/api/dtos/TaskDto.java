package com.petproject.workflow_server.api.dtos;

import com.petproject.workflow_server.store.entities.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String description;
    private LocalDate creation;
    private LocalDate deadline;
    private TaskStatus status;
    private String executorId;
    private String inspectorId;
}
