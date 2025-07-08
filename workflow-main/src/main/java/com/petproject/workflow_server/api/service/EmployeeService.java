package com.petproject.workflow_server.api.service;

import com.petproject.workflow_server.store.entities.Absence;
import com.petproject.workflow_server.store.entities.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    Employee getEmployeeById(UUID id);

    List<Employee> getAllEmployees();

    void deleteEmployee(UUID id);

    Employee save(Employee employee);

    Absence addAbsenceToEmployee(UUID employeeId, Absence absence);

    List<Employee> findEmployeesByPatternName(String patternName);
}
