package com.petproject.workflow.api;

import com.petproject.workflow.store.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public Department maptoDepartment(DepartmentDto departmentDto) {
        if(departmentDto == null) { return null; }
        return new Department(
                departmentDto.getId(),
                departmentDto.getName()
        );
    }

    public DepartmentDto maptoDepartmentDto(Department department) {
        if(department == null) { return null; }
        return new DepartmentDto(
                department.getId(),
                department.getName()
        );
    }
}
