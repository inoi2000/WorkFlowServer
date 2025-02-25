package com.petproject.workflow_server.dtos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.petproject.workflow_server.entities.BusinessTrip;
import com.petproject.workflow_server.entities.Department;
import com.petproject.workflow_server.entities.Employee;
import com.petproject.workflow_server.entities.Vacation;

import java.util.List;
import java.util.UUID;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class EmployeeDto {

    private UUID id;

    private String name;

    private List<BusinessTrip> businessTrips;

    private List<Vacation> vacations;

    private Department department;

    public EmployeeDto(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.businessTrips = employee.getBusinessTrips();
        this.vacations = employee.getVacations();
        this.department = employee.getDepartment();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BusinessTrip> getBusinessTrips() {
        return businessTrips;
    }

    public void setBusinessTrips(List<BusinessTrip> businessTrips) {
        this.businessTrips = businessTrips;
    }

    public List<Vacation> getVacations() {
        return vacations;
    }

    public void setVacations(List<Vacation> vacations) {
        this.vacations = vacations;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
