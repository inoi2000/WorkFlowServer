package com.petproject.workflow_server.dao;


import com.petproject.workflow_server.entities.BusinessTrip;
import com.petproject.workflow_server.entities.Employee;
import com.petproject.workflow_server.entities.Vacation;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository {
    Employee getEmployeeById(UUID id);

    List<Employee> getAllEmployees();

    void deleteEmployee(UUID id);

    void createEmployee(Employee employee);

    void updateEmployee(Employee employee);

    void addBusinessTripToEmployee(UUID employeeId, BusinessTrip businessTrip);

    void addVacationToEmployee(UUID employeeId, Vacation vacation);
}
