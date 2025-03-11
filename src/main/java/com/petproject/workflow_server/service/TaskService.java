package com.petproject.workflow_server.service;

import com.petproject.workflow_server.dtos.TaskDto;
import com.petproject.workflow_server.entities.Task;
import com.petproject.workflow_server.entities.TaskStatus;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    Task save(TaskDto task);

    List<Task> getAllExecutingTasks(UUID empId);

    List<Task> getAllInspectingTasks(UUID empId);

    List<Task> getInspectingTasksByStatus(UUID empId, TaskStatus status);

    void updateTaskStatus(UUID taskId, TaskStatus newStatus);
}
