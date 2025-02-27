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

    Employee save(Employee employee);

    List<BusinessTrip> getAllBusinessTrips(UUID employeeId);

    BusinessTrip addBusinessTripToEmployee(UUID employeeId, BusinessTrip businessTrip);

    List<Vacation> getAllVacations(UUID employeeId);

    Vacation addVacationToEmployee(UUID employeeId, Vacation vacation);
}
