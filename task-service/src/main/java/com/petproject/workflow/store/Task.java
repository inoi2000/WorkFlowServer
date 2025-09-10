package com.petproject.workflow.store;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
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

    @Column(name = "creation", nullable = false)
    private LocalDate creation;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    @Column(name = "destination")
    private String destination;

    @Column(name = "should_be_inspected")
    private boolean shouldBeInspected;

    @Column(name = "executor_id", columnDefinition = "BINARY(16)")
    private UUID executor;

    @Column(name = "inspector_id", columnDefinition = "BINARY(16)")
    private UUID inspector;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "task_id")
    private List<Comment> comments;
}