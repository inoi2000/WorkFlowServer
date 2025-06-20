package com.petproject.workflow_server.api.service;

import com.petproject.workflow_server.store.repositories.DepartmentRepository;
import com.petproject.workflow_server.store.entities.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public Department getDepartmentById(UUID id) {
        return departmentRepository.getDepartmentById(id);
    }

    @Override
    @Transactional
    public List<Department> getAllDepartments() {
        return departmentRepository.getAllDepartments();
    }

    @Override
    @Transactional
    public void deleteDepartment(UUID id) {
        departmentRepository.deleteDepartment(id);
    }

    @Override
    @Transactional
    public void createDepartment(Department department) {
        departmentRepository.createDepartment(department);
    }

    @Override
    @Transactional
    public void updateDepartment(Department department) {
        departmentRepository.updateDepartment(department);
    }

    @Override
    @Transactional
    public void addEmployeeToDepartment(UUID departmentId, UUID employeeId) {
        departmentRepository.addEmployeeToDepartment(departmentId, employeeId);
    }

    @Override
    @Transactional
    public List<Department> findDepartmentsByPatternName(String patternName) {
        return departmentRepository.findDepartmentsByPatternName(patternName);
    }
}
