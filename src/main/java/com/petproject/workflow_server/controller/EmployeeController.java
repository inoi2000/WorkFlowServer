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
        Employee employee = employeeService.getEmployeeById(UUID.fromString(id));
        return employee;
    }

    @PostMapping("/")
    public Employee createEmployee(@RequestBody Employee employee) {
        employeeService.createEmployee(employee);
        return employee;
    }

    @PutMapping("/")
    public Employee updateEmployee(@RequestBody Employee employee) {
        employeeService.updateEmployee(employee);
        return employee;
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable("id") String id) {
        employeeService.deleteEmployee(UUID.fromString(id));
        return "Employee with id " + id + " deleted successfully";
    }

//    @PostMapping(/)
//    public void addBusinessTripToEmployee(UUID employeeId, BusinessTrip businessTrip) {
//
//    }
//
//    public void addVacationToEmployee(UUID employeeId, Vacation vacation) {
//
//    }
}
