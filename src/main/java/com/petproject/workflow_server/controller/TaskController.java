package com.petproject.workflow_server.controller;

import com.petproject.workflow_server.dtos.TaskDto;
import com.petproject.workflow_server.entities.Task;
import com.petproject.workflow_server.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/")
    public Task createTaskToEmployee(@RequestBody TaskDto task) {
        return taskService.save(task);
    }
}
