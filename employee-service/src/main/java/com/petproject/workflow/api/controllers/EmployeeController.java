package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.EmployeeDto;
import com.petproject.workflow.api.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/employees", produces = "application/json")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/")
    public Iterable<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable("id") UUID id) {
        return employeeService.getEmployeeById(id).map(dto ->
                        new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto dto) {
        return employeeService.saveEmployee(dto);
    }

    @GetMapping("/subordinate/{id}")
    public Iterable<EmployeeDto> getSubordinateEmployees(@PathVariable("id") UUID id) {
        return employeeService.getSubordinateEmployees(id);
    }

    @GetMapping("/drivers")
    public Iterable<EmployeeDto> getDriversEmployees() {
        return employeeService.getEmployeeWithPositionName("Водитель");
    }

    @GetMapping("/search/{query}")
    public Iterable<EmployeeDto> getDriversEmployees(@PathVariable("query") String query) {
        return employeeService.getAllEmployeesByNameOrPositionName(query);
    }

    @PostMapping("/batch")
    public Iterable<EmployeeDto> getBatchEmployees(@RequestBody Iterable<UUID> uuids) {
        return employeeService.getBatchEmployees(uuids);
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
