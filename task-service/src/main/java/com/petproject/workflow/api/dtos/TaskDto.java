package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.Comment;
import com.petproject.workflow.store.Task;
import com.petproject.workflow.store.TaskPriority;
import com.petproject.workflow.store.TaskStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private UUID id;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate creation;
    private LocalDate deadline;
    private String destination;
    private boolean shouldBeInspected;
    private EmployeeDto executor;
    private EmployeeDto inspector;
    private List<Comment> comments;

    public TaskDto(Task task, EmployeeDto executor, EmployeeDto inspector) {
        this.id = task.getId();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.priority = task.getPriority();
        this.creation = task.getCreation();
        this.deadline = task.getDeadline();
        this.destination = task.getDestination();
        this.shouldBeInspected = task.isShouldBeInspected();
        this.executor = executor;
        this.inspector = inspector;
        this.comments = task.getComments();
    }

    public Task toTask() {
        return new Task(this.id,
                this.description,
                this.status,
                this.priority,
                this.creation,
                this.deadline,
                this.destination,
                this.shouldBeInspected,
                this.executor.getId(),
                this.inspector.getId(),
                this.comments
        );
    }
}
