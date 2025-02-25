package com.petproject.workflow_server.service;

import com.petproject.workflow_server.entities.BusinessTrip;
import com.petproject.workflow_server.entities.Employee;
import com.petproject.workflow_server.entities.Vacation;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    Employee getEmployeeById(UUID id);

    List<Employee> getAllEmployees();

    void deleteEmployee(UUID id);

    void createEmployee(Employee employee);

    void updateEmployee(Employee employee);

    BusinessTrip addBusinessTripToEmployee(UUID employeeId, BusinessTrip businessTrip);

    Vacation addVacationToEmployee(UUID employeeId, Vacation vacation);
}
