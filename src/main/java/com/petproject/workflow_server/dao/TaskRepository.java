package com.petproject.workflow_server.dao;

import com.petproject.workflow_server.entities.Task;
import com.petproject.workflow_server.entities.TaskStatus;

import java.util.List;
import java.util.UUID;

public interface TaskRepository {

    Task getTaskById(UUID taskId);

    Task save(Task task);

    List<Task> getAllExecutingTasks(UUID empId);

    List<Task> getAllInspectingTasks(UUID empId);

    List<Task> getInspectingTasksByStatus(UUID empId, TaskStatus status);

    void updateTaskStatus(UUID taskId, TaskStatus newStatus);
}
