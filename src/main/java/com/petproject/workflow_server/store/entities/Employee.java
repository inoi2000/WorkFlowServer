package com.petproject.workflow_server.store.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.petproject.workflow_server.api.serialization.DepartmentSerializer;
import com.petproject.workflow_server.api.serialization.TaskListSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "id", columnDefinition = "BYNARY(16)")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private String position;

    @JsonSerialize(using = DepartmentSerializer.class)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "employee_id")
    private List<Absence> absences;

    @JsonSerialize(using = TaskListSerializer.class)
    @OneToMany(mappedBy = "inspector", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Task> inspectionTasks;

    @JsonSerialize(using = TaskListSerializer.class)
    @OneToMany(mappedBy = "executor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Task> executionTasks;

    public void addAbsence(Absence absence) {
        if (this.absences == null) {
            this.absences = new ArrayList<>();
        }
        this.absences.add(absence);
    }
}
