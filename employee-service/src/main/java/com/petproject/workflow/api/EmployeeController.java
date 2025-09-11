package com.petproject.workflow.api;

import com.petproject.workflow.store.Employee;
import com.petproject.workflow.store.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/employees", produces="application/json")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping("/")
    public Iterable<EmployeeDto> getAllEmployees() {
        Iterable<Employee> employees = employeeRepository.findAll();
        return StreamSupport.stream(employees.spliterator(), false).map(
                employeeMapper::mapToEmployeeDto
        ).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable("id") String id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(UUID.fromString(id));
        return optionalEmployee
                .map(employee ->
                        new ResponseEntity<>(employeeMapper.mapToEmployeeDto(employee), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto dto) {
        Employee employee = employeeMapper.mapToEmployee(dto);
        employee = employeeRepository.save(employee);
        return employeeMapper.mapToEmployeeDto(employee);
    }

    @GetMapping("/subordinate/{id}")
    public Iterable<EmployeeDto> getSubordinateEmployees(@PathVariable("id") String id) {
        List<Employee> employees = employeeRepository.findAllEmployeesWithLowerPositionLevel(UUID.fromString(id));
        return employees.stream().map(employeeMapper::mapToEmployeeDto).toList();
    }

    @PostMapping("/batch")
    public Iterable<EmployeeDto> getBatchEmployees(@RequestBody Iterable<UUID> uuids) {
        List<Employee> employees = employeeRepository.findAllById(uuids);
        return employees.stream().map(employeeMapper::mapToEmployeeDto).toList();
    }
}
