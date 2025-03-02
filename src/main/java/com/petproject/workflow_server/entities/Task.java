package com.petproject.workflow_server.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.petproject.workflow_server.serialization.DepartmentSerializer;
import com.petproject.workflow_server.serialization.EmployeeSerializer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @Column(name = "creation", nullable = false)
    private LocalDate creation;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @JsonSerialize(using = EmployeeSerializer.class)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "executor_id")
    private Employee executor;

    @JsonSerialize(using = EmployeeSerializer.class)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "inspector_id")
    private Employee inspector;
}
