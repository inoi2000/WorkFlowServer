package com.petproject.workflow_server.store.repositories;


import com.petproject.workflow_server.store.entities.Absence;
import com.petproject.workflow_server.store.entities.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository {
    Employee getEmployeeById(UUID id);

    List<Employee> getAllEmployees();

    void deleteEmployee(UUID id);

    Employee save(Employee employee);

    List<Absence> getAllAbsences(UUID employeeId);

    Absence addAbsenceToEmployee(UUID employeeId, Absence absence);

    List<Employee> findEmployeesByPatternName(String patternName);
}
