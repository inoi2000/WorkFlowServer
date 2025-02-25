package com.petproject.workflow_server.service;

import com.petproject.workflow_server.entities.Department;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {

    Department getDepartmentById(UUID id);

    List<Department> getAllDepartments();

    void deleteDepartment(UUID id);

    void createDepartment(Department department);

    void updateDepartment(Department department);

    void addEmployeeToDepartment(UUID departmentId, UUID employeeId);

}
