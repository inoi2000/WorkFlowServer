package com.petproject.workflow.api;

import com.petproject.workflow.store.Task;
import com.petproject.workflow.store.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/tasks", produces = "application/json")
public class TaskController {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        task.setId(UUID.randomUUID());
        return taskRepository.save(task);
    }
}
