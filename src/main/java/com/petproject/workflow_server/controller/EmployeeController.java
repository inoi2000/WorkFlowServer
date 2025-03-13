package com.petproject.workflow_server.controller;

import com.petproject.workflow_server.entities.BusinessTrip;
import com.petproject.workflow_server.entities.Employee;
import com.petproject.workflow_server.entities.Vacation;
import com.petproject.workflow_server.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable("id") String id) {
        return employeeService.getEmployeeById(UUID.fromString(id));
    }

    @PostMapping("/")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @PutMapping("/")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable("id") String id) {
        employeeService.deleteEmployee(UUID.fromString(id));
        return "Employee with id " + id + " deleted successfully";
    }

    @PostMapping("/business_trips/{employeeId}")
    public BusinessTrip createBusinessTripToEmployee(
            @PathVariable("employeeId") String employeeId,
            @RequestBody BusinessTrip businessTrip) {
        return employeeService.addBusinessTripToEmployee(UUID.fromString(employeeId), businessTrip);
    }

    @PostMapping("/vacations/{employeeId}")
    public Vacation createVacationToEmployee(
            @PathVariable("employeeId") String employeeId,
            @RequestBody Vacation vacation) {
        return employeeService.addVacationToEmployee(UUID.fromString(employeeId), vacation);
    }

    @GetMapping("/name/{name}")
    public List<Employee> findEmployeesByPatternName(@PathVariable("name") String patternName) {
        return employeeService.findEmployeesByPatternName(patternName);
    }
}
