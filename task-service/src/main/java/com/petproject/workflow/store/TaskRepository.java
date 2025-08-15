package com.petproject.workflow.store;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TaskRepository extends CrudRepository<Task, UUID> {
    Iterable<Task> findAllByExecutor(UUID executorId);
    Iterable<Task> findAllByInspector(UUID inspectorId);
}
