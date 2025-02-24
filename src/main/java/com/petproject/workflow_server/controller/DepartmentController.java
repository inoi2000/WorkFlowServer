package com.petproject.workflow_server.controller;

import com.petproject.workflow_server.entities.Department;
import com.petproject.workflow_server.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    @GetMapping("/")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartment(@PathVariable("id") String id) {
        return departmentService.getDepartmentById(UUID.fromString(id));
    }

    @PostMapping("/")
    public Department createDepartment(@RequestBody Department department) {
        departmentService.createDepartment(department);
        return department;
    }

    @PutMapping("/")
    public Department updateDepartment(@RequestBody Department department) {
        departmentService.updateDepartment(department);
        return department;
    }

    @DeleteMapping("/{id}")
    public String deleteDepartment(@PathVariable("id") String id) {
        departmentService.deleteDepartment(UUID.fromString(id));
        return "Department with id " + id + " deleted successfully";
    }

    @PostMapping("/{departmentId}/{employeeId}")
    public String addEmployeeToDepartment(
            @PathVariable("departmentId") String departmentId,
            @PathVariable("employeeId") String employeeId) {
        departmentService.addEmployeeToDepartment(UUID.fromString(departmentId), UUID.fromString(employeeId));
        return "Employee with id " + employeeId + " added into department with id " + departmentId + " successfully";
    }
}
