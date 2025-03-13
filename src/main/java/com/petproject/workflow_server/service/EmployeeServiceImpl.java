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
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public BusinessTrip addBusinessTripToEmployee(UUID employeeId, BusinessTrip businessTrip) {
        return employeeRepository.addBusinessTripToEmployee(employeeId, businessTrip);
    }

    @Override
    @Transactional
    public Vacation addVacationToEmployee(UUID employeeId, Vacation vacation) {
        return employeeRepository.addVacationToEmployee(employeeId, vacation);
    }

    @Override
    @Transactional
    public List<Employee> findEmployeesByPatternName(String patternName) {
        return employeeRepository.findEmployeesByPatternName(patternName);
    }
}
