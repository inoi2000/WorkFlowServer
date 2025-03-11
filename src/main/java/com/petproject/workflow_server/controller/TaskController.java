package com.petproject.workflow_server.controller;

import com.petproject.workflow_server.dtos.TaskDto;
import com.petproject.workflow_server.entities.Task;
import com.petproject.workflow_server.entities.TaskStatus;
import com.petproject.workflow_server.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //Общая работа с задачами
    @PostMapping("/")
    public Task createTask(@RequestBody TaskDto task) {
        return taskService.save(task);
    }


    //Работа с выполенением задач
    @GetMapping("/execution/{empId}")
    private List<Task> getAllExecutingTasks(@PathVariable("empId") String empId) {
        return taskService.getAllExecutingTasks(UUID.fromString(empId));
    }

    @PutMapping("/execution/{taskId}")
    private void submitTaskOnApproval(@PathVariable("taskId") String taskId) {
        taskService.updateTaskStatus(UUID.fromString(taskId), TaskStatus.ON_APPROVAL);
    }


    //Работа с одобрением задач
    @GetMapping("/approval/{empId}")
    private List<Task> getOnApprovalInspectingTasks(@PathVariable("empId") String empId) {
        return taskService.getInspectingTasksByStatus(UUID.fromString(empId), TaskStatus.ON_APPROVAL);
    }

    @PutMapping("/approval/{taskId}")
    public void approveTask(@PathVariable("taskId") String taskId) {
        taskService.updateTaskStatus(UUID.fromString(taskId), TaskStatus.COMPLETED);
    }


    //Работа с порученными задачами
    @GetMapping("/inspection/{empId}")
    private List<Task> getAllInspectingTasks(@PathVariable("empId") String empId) {
        return taskService.getAllInspectingTasks(UUID.fromString(empId));
    }
}
