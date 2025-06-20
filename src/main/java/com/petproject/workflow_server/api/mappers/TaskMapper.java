package com.petproject.workflow_server.api.mappers;

import com.petproject.workflow_server.api.dtos.TaskDto;
import com.petproject.workflow_server.store.entities.Employee;
import com.petproject.workflow_server.store.entities.Task;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TaskMapper {

    public Task mapDtoToEntity(TaskDto taskDto, Employee executor, Employee inspector) {
        return Task.builder()
                .id(UUID.randomUUID())
                .description(taskDto.getDescription())
                .creation(taskDto.getCreation())
                .deadline(taskDto.getDeadline())
                .status(taskDto.getStatus())
                .executor(executor)
                .inspector(inspector)
                .build();
    }
}
