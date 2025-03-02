package com.petproject.workflow_server.dao;

import com.petproject.workflow_server.entities.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(UUID.randomUUID());
            entityManager.persist(task);
        } else {
            entityManager.merge(task);
        }
        return task;
    }
}
