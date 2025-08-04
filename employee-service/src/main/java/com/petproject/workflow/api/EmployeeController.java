package com.petproject.workflow.api;

import com.petproject.workflow.store.Employee;
import com.petproject.workflow.store.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/employees", produces="application/json")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/")
    public Iterable<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") String id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(UUID.fromString(id));
        return optionalEmployee
                .map(employee -> new ResponseEntity<>(employee, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/subordinate/{id}")
    public Iterable<Employee> getSubordinateEmployees(@PathVariable("id") String id) {
        return employeeRepository.findAllEmployeesWithLowerPositionLevel(UUID.fromString(id));
    }
}
