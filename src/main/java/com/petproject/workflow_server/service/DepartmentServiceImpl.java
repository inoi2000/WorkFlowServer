package com.petproject.workflow_server.service;

import com.petproject.workflow_server.dao.DepartmentRepository;
import com.petproject.workflow_server.dao.EmployeeRepository;
import com.petproject.workflow_server.entities.Department;
import com.petproject.workflow_server.entities.Employee;
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
}
