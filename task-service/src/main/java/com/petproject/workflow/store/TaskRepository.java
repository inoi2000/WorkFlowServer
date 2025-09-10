package com.petproject.workflow.store;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends CrudRepository<Task, UUID> {
    List<Task> findAllByExecutor(UUID executorId);
    List<Task> findAllByInspector(UUID inspectorId);
}
