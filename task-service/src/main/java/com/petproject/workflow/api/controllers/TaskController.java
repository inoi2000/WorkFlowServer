package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.TaskDto;
import com.petproject.workflow.api.services.TaskService;
import com.petproject.workflow.store.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/tasks", produces = "application/json")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable UUID id) {
        Optional<TaskDto> optionalTask = taskService.getTaskById(id);
        return optionalTask
                .map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/executors/{executorId}")
    public Iterable<TaskDto> getAllTasksByExecutor(@PathVariable UUID executorId) {
        return taskService.getAllTasksByExecutor(executorId);
    }

    @GetMapping("/inspectors/{inspectorId}")
    public Iterable<TaskDto> getAllTasksByInspector(@PathVariable UUID inspectorId) {
        return taskService.getAllTasksByInspector(inspectorId);
    }

    //    создание новой задачи
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@RequestBody TaskDto task) {
        return taskService.createTask(task);
    }

    //    принятие задачи на выполнение
    @PostMapping("/{taskId}/accept")
    public ResponseEntity<TaskDto> acceptTask(@PathVariable UUID taskId,
                                           JwtAuthenticationToken authentication) {
        try {
            return taskService.acceptTask(taskId, getUserId(authentication))
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //    отправка на утверждение
    @PostMapping("/{taskId}/submit")
    public ResponseEntity<TaskDto> submitTask(@PathVariable UUID taskId,
                                           JwtAuthenticationToken authentication) {
        try {
            return taskService.submitTask(taskId, getUserId(authentication))
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //    утверждение задачи
    @PostMapping("/{taskId}/approve")
    public ResponseEntity<TaskDto> approvalTask(@PathVariable UUID taskId,
                                           JwtAuthenticationToken authentication) {
        try {
            return taskService.approvalTask(taskId, getUserId(authentication))
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //    отказ в утверждении
    @PostMapping("/{taskId}/reject")
    public ResponseEntity<TaskDto> rejectTask(@PathVariable UUID taskId,
                                             JwtAuthenticationToken authentication) {
        try {
            return taskService.rejectTask(taskId, getUserId(authentication))
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //    отмена задачи
    @PostMapping("/{taskId}/cancel")
    public ResponseEntity<TaskDto> cancelTask(@PathVariable UUID taskId,
                                           JwtAuthenticationToken authentication) {
        try {
            return taskService.cancelTask(taskId, getUserId(authentication))
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    private UUID getUserId(JwtAuthenticationToken authentication) {
        return UUID.fromString(authentication.getToken().getClaimAsString("id"));
    }
}
