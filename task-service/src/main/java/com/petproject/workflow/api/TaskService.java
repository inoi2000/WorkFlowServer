package com.petproject.workflow.api;

import com.petproject.workflow.store.Task;
import com.petproject.workflow.store.TaskRepository;
import com.petproject.workflow.store.TaskStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Optional<Task> acceptTask(UUID taskId, UUID userId) throws IllegalStateException {
        return taskRepository.findById(taskId)
                .filter(task -> task.getExecutor().equals(userId))
                .map(task -> {
                    if (task.getStatus() != TaskStatus.NEW) {
                        throw new IllegalStateException();
                    }
                    task.setStatus(TaskStatus.IN_PROGRESS);
                    return taskRepository.save(task);
                });
    }

    public Optional<Task> submitTask(UUID taskId, UUID userId) throws IllegalStateException {
        return taskRepository.findById(taskId)
                .filter(task -> task.getExecutor().equals(userId))
                .map(task -> {
                    if (task.getStatus() != TaskStatus.IN_PROGRESS && task.getStatus() != TaskStatus.NOT_APPROVAL) {
                        throw new IllegalStateException();
                    }
                    if (task.isShouldBeInspected()) { task.setStatus(TaskStatus.ON_APPROVAL); }
                    else { task.setStatus(TaskStatus.COMPLETED); }
                    return taskRepository.save(task);
                });
    }

    public Optional<Task> approvalTask(UUID taskId, UUID userId) {
        return taskRepository.findById(taskId)
                .filter(task -> task.getInspector().equals(userId))
                .map(task -> {
                    task.setStatus(TaskStatus.COMPLETED);
                    return taskRepository.save(task);
                });
    }

    public Optional<Task> rejectTask(UUID taskId, UUID userId) throws IllegalStateException {
        return taskRepository.findById(taskId)
                .filter(task -> task.getInspector().equals(userId))
                .map(task -> {
                    if (task.getStatus() != TaskStatus.ON_APPROVAL) {
                        throw new IllegalStateException();
                    }
                    task.setStatus(TaskStatus.NOT_APPROVAL);
                    return taskRepository.save(task);
                });
    }

    public Optional<Task> cancelTask(UUID taskId, UUID userId) {
        return taskRepository.findById(taskId)
                .filter(task -> task.getInspector().equals(userId))
                .map(task -> {
                    task.setStatus(TaskStatus.CANCELED);
                    return taskRepository.save(task);
                });
    }
}
