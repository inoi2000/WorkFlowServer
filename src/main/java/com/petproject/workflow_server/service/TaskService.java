package com.petproject.workflow_server.service;

import com.petproject.workflow_server.dtos.TaskDto;
import com.petproject.workflow_server.entities.Task;

public interface TaskService {
    Task save(TaskDto task);
}
