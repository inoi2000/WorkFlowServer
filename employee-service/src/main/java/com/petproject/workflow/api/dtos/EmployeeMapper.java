package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.entities.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    private final PositionMapper positionMapper;
    private final DepartmentMapper departmentMapper;

    public EmployeeMapper(
            PositionMapper positionMapper,
            DepartmentMapper departmentMapper) {
        this.positionMapper = positionMapper;
        this.departmentMapper = departmentMapper;
    }

    public Employee mapToEmployee(EmployeeDto employeeDto) {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getName(),
                employeeDto.getPhone(),
                employeeDto.getEmail(),
                positionMapper.mapToPosition(employeeDto.getPosition()),
                departmentMapper.maptoDepartment(employeeDto.getDepartment())
        );
    }

    public EmployeeDto mapToEmployeeDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getPhone(),
                employee.getEmail(),
                positionMapper.mapToPositionDto(employee.getPosition()),
                departmentMapper.maptoDepartmentDto(employee.getDepartment())
        );
    }
}
