package com.petproject.workflow.api;

import com.petproject.workflow.store.Task;
import com.petproject.workflow.store.TaskRepository;
import com.petproject.workflow.store.TaskStatus;
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
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskRepository taskRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        Optional<Task> optionalTask = taskRepository.findById(UUID.fromString(id));
        return optionalTask
                .map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/executors/{executorId}")
    public Iterable<Task> getAllTasksByExecutor(@PathVariable String executorId) {
        UUID uuid = UUID.fromString(executorId);
        return taskRepository.findAllByExecutor(uuid);
    }

    @GetMapping("/inspectors/{inspectorId}")
    public Iterable<Task> getAllTasksByInspector(@PathVariable String inspectorId) {
        UUID uuid = UUID.fromString(inspectorId);
        return taskRepository.findAllByInspector(uuid);
    }

    //    создание новой задачи
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        task.setId(UUID.randomUUID());
        task.setStatus(TaskStatus.NEW);
        return taskRepository.save(task);
    }

    //    принятие задачи на выполнение
    @PostMapping("/{taskId}/accept")
    public ResponseEntity<Task> acceptTask(@PathVariable UUID taskId,
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
    public ResponseEntity<Task> submitTask(@PathVariable UUID taskId,
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
    public ResponseEntity<Task> approvalTask(@PathVariable UUID taskId,
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
    public ResponseEntity<Task> rejectTask(@PathVariable UUID taskId,
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
    public ResponseEntity<Task> cancelTask(@PathVariable UUID taskId,
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
