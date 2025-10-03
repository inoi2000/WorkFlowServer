package com.petproject.workflow.api;

import com.petproject.workflow.store.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    private final PositionMapper positionMapper;

    public EmployeeMapper(PositionMapper positionMapper) {
        this.positionMapper = positionMapper;
    }

    public Employee mapToEmployee(EmployeeDto employeeDto) {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getName(),
                employeeDto.getPhone(),
                positionMapper.mapToPosition(employeeDto.getPosition())
        );
    }

    public EmployeeDto mapToEmployeeDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getPhone(),
                positionMapper.mapToPositionDto(employee.getPosition())
        );
    }
}
