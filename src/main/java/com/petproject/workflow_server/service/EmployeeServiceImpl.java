package com.petproject.workflow_server.service;

import com.petproject.workflow_server.dao.EmployeeRepository;
import com.petproject.workflow_server.entities.BusinessTrip;
import com.petproject.workflow_server.entities.Employee;
import com.petproject.workflow_server.entities.Vacation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public Employee getEmployeeById(UUID id) {
        return employeeRepository.getEmployeeById(id);
    }

    @Override
    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    @Override
    @Transactional
    public void deleteEmployee(UUID id) {
        employeeRepository.deleteEmployee(id);
    }

    @Override
    @Transactional
    public void createEmployee(Employee employee) {
        employeeRepository.createEmployee(employee);
    }

    @Override
    @Transactional
    public void updateEmployee(Employee employee) {
        employeeRepository.updateEmployee(employee);
    }

    @Override
    @Transactional
    public void addBusinessTripToEmployee(UUID employeeId, BusinessTrip businessTrip) {
        employeeRepository.addBusinessTripToEmployee(employeeId, businessTrip);
    }

    @Override
    @Transactional
    public void addVacationToEmployee(UUID employeeId, Vacation vacation) {
        employeeRepository.addVacationToEmployee(employeeId, vacation);
    }
}
