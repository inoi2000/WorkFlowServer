package com.petproject.workflow_server.store.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.petproject.workflow_server.api.serialization.EmployeeSerializer;
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
    @Column(name = "id", columnDefinition = "BYNARY(16)")
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

    @JsonSerialize(using = EmployeeSerializer.class)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "executor_id")
    private Employee executor;

    @JsonSerialize(using = EmployeeSerializer.class)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "inspector_id")
    private Employee inspector;

    //TODO @JsonSerialize
    @OneToMany(mappedBy = "task", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Comment> comments;
}
