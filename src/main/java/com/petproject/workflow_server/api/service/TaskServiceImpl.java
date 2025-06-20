package com.petproject.workflow_server.api.service;

import com.petproject.workflow_server.store.repositories.EmployeeRepository;
import com.petproject.workflow_server.store.repositories.TaskRepository;
import com.petproject.workflow_server.api.dtos.TaskDto;
import com.petproject.workflow_server.store.entities.Task;
import com.petproject.workflow_server.store.entities.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TaskRepository taskRepository;


    @Override
    @Transactional
    public Task save(TaskDto taskDto) {
        var executor = employeeRepository.getEmployeeById(UUID.fromString(taskDto.getExecutorId()));
        var inspector = employeeRepository.getEmployeeById(UUID.fromString(taskDto.getInspectorId()));
        var task = Task.builder()
                .description(taskDto.getDescription())
                .creation(taskDto.getCreation())
                .deadline(taskDto.getDeadline())
                .status(TaskStatus.NEW)
                .executor(executor)
                .inspector(inspector)
                .build();
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public List<Task> getAllExecutingTasks(UUID empId) {
        return taskRepository.getAllExecutingTasks(empId);
    }

    @Override
    @Transactional
    public List<Task> getAllInspectingTasks(UUID empId) {
        return taskRepository.getAllInspectingTasks(empId);
    }

    @Override
    @Transactional
    public List<Task> getInspectingTasksByStatus(UUID empId, TaskStatus status) {
        return taskRepository.getInspectingTasksByStatus(empId, status);
    }

    @Override
    @Transactional
    public void updateTaskStatus(UUID taskId, TaskStatus newStatus) {
        taskRepository.updateTaskStatus(taskId, newStatus);
    }
}
