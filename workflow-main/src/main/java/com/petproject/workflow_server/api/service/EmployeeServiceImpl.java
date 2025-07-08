package com.petproject.workflow_server.api.service;

import com.petproject.workflow_server.store.entities.Absence;
import com.petproject.workflow_server.store.repositories.EmployeeRepository;
import com.petproject.workflow_server.store.entities.Employee;
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
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public Absence addAbsenceToEmployee(UUID employeeId, Absence absence) {
        return employeeRepository.addAbsenceToEmployee(employeeId, absence);
    }

    @Override
    @Transactional
    public List<Employee> findEmployeesByPatternName(String patternName) {
        return employeeRepository.findEmployeesByPatternName(patternName);
    }
}
