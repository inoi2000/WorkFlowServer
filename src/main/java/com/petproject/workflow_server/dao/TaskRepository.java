package com.petproject.workflow_server.dao;

import com.petproject.workflow_server.entities.Task;

public interface TaskRepository {
    Task save(Task task);
}
