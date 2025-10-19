package com.petproject.workflow.store;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private TaskPriority priority;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    @Column(name = "should_be_inspected")
    private boolean shouldBeInspected;

    @Column(name = "executor_id", columnDefinition = "BINARY(16)")
    private UUID executor;

    @Column(name = "inspector_id", columnDefinition = "BINARY(16)")
    private UUID inspector;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "task_id")
    private List<Comment> comments;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return shouldBeInspected == task.shouldBeInspected &&
                Objects.equals(id, task.id) &&
                Objects.equals(description, task.description) &&
                status == task.status &&
                priority == task.priority &&
                Objects.equals(createdAt, task.createdAt) &&
                Objects.equals(deadline, task.deadline) &&
                Objects.equals(executor, task.executor) &&
                Objects.equals(inspector, task.inspector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                description,
                status,
                priority,
                createdAt,
                deadline,
                shouldBeInspected,
                executor,
                inspector
        );
    }
}