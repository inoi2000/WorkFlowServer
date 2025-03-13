package com.petproject.workflow_server.dao;

import com.petproject.workflow_server.entities.Department;

import java.util.List;
import java.util.UUID;

public interface DepartmentRepository {

    Department getDepartmentById(UUID id);

    List<Department> getAllDepartments();

    void deleteDepartment(UUID id);

    void createDepartment(Department department);

    void updateDepartment(Department department);

    void addEmployeeToDepartment(UUID departmentId, UUID employeeId);

    List<Department> findDepartmentsByPatternName(String patternName);
}
