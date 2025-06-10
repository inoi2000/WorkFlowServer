package com.petproject.workflow_server.store.repositories;

import com.petproject.workflow_server.store.entities.Task;
import com.petproject.workflow_server.store.entities.TaskStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Task getTaskById(UUID taskId) {
        return entityManager.find(Task.class, taskId);
    }

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

    @Override
    public List<Task> getAllExecutingTasks(UUID empId) {
        Query query = entityManager.createQuery("SELECT t FROM Task t WHERE t.executor.id = :empId");
        query.setParameter("empId", empId);
        return query.getResultList();
    }

    @Override
    public List<Task> getAllInspectingTasks(UUID empId) {
        Query query = entityManager.createQuery("SELECT t FROM Task t WHERE t.inspector.id = :empId");
        query.setParameter("empId", empId);
        return query.getResultList();
    }

    @Override
    public List<Task> getInspectingTasksByStatus(UUID empId, TaskStatus status) {
        Query query = entityManager.createQuery(
                "SELECT t FROM Task t WHERE t.inspector.id = :empId AND t.status = :status"
        );
        query.setParameter("empId", empId);
        query.setParameter("status", status);
        return query.getResultList();
    }

    @Override
    public void updateTaskStatus(UUID taskId, TaskStatus newStatus) {
        Task task = getTaskById(taskId);
        task.setStatus(newStatus);
        entityManager.merge(task);
    }
}
