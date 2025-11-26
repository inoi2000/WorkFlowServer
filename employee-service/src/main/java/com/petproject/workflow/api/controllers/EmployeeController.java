package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.EmployeeDto;
import com.petproject.workflow.api.dtos.EmployeeMapper;
import com.petproject.workflow.api.services.EmployeeService;
import com.petproject.workflow.store.entities.Employee;
import com.petproject.workflow.store.repositories.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/employees", produces = "application/json")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @GetMapping("/")
    public Iterable<EmployeeDto> getAllEmployees() {
        Iterable<Employee> employees = employeeRepository.findAll();
        return StreamSupport.stream(employees.spliterator(), false).map(
                employeeMapper::mapToEmployeeDto
        ).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable("id") UUID id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee
                .map(employee ->
                        new ResponseEntity<>(employeeMapper.mapToEmployeeDto(employee), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto dto) {
        dto.setId(UUID.randomUUID());
        if (dto.getPosition().getId() == null) {
            dto.getPosition().setId(UUID.randomUUID());
        }
        Employee employee = employeeMapper.mapToEmployee(dto);
        employee = employeeRepository.save(employee);
        return employeeMapper.mapToEmployeeDto(employee);
    }

    @GetMapping("/subordinate/{id}")
    public Iterable<EmployeeDto> getSubordinateEmployees(@PathVariable("id") String id) {
        List<Employee> employees = employeeRepository.findAllEmployeesWithLowerPositionLevel(UUID.fromString(id));
        return employees.stream().map(employeeMapper::mapToEmployeeDto).toList();
    }

    @GetMapping("/drivers")
    public Iterable<EmployeeDto> getDriversEmployees() {
        List<Employee> employees = employeeRepository.findAllEmployeesWithPositionName("Водитель");
        return employees.stream().map(employeeMapper::mapToEmployeeDto).toList();
    }

    @PostMapping("/batch")
    public Iterable<EmployeeDto> getBatchEmployees(@RequestBody Iterable<UUID> uuids) {
        List<Employee> employees = employeeRepository.findAllById(uuids);
        return employees.stream().map(employeeMapper::mapToEmployeeDto).toList();
    }

    // Work with avatars
    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> downloadFile(@PathVariable UUID id) {
        return employeeService.getEmployeePhoto(id)
                .map(fileResponse -> ResponseEntity.ok()
                        .contentType(fileResponse.getContentType())
                        .body(fileResponse.getData()))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
