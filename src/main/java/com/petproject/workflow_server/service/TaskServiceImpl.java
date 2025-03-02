package com.petproject.workflow_server.service;

import com.petproject.workflow_server.dao.EmployeeRepository;
import com.petproject.workflow_server.dao.TaskRepository;
import com.petproject.workflow_server.dtos.TaskDto;
import com.petproject.workflow_server.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .status(taskDto.getStatus())
                .executor(executor)
                .inspector(inspector)
                .build();
        return taskRepository.save(task);
    }
}
