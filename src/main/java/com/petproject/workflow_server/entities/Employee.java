package com.petproject.workflow_server.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.petproject.workflow_server.serialization.DepartmentSerializer;
import com.petproject.workflow_server.serialization.TaskListSerializer;
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

    @JsonSerialize(using = TaskListSerializer.class)
    @OneToMany(mappedBy = "inspector", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Task> inspectionTasks;

    @JsonSerialize(using = TaskListSerializer.class)
    @OneToMany(mappedBy = "executor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Task> executionTasks;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "employee_id")
    private List<BusinessTrip> businessTrips;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "employee_id")
    private List<Vacation> vacations;

    @JsonSerialize(using = DepartmentSerializer.class)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "department_id")
    private Department department;

    public void addBusinessTrip(BusinessTrip businessTrip) {
        if (this.businessTrips == null) {
            this.businessTrips = new ArrayList<>();
        }
        this.businessTrips.add(businessTrip);
    }

    public void addVacation(Vacation vacation) {
        if (this.vacations == null) {
            this.vacations = new ArrayList<>();
        }
        this.vacations.add(vacation);
    }
}
