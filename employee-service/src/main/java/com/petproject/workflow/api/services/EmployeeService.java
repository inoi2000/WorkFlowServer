package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.EmployeeDto;
import com.petproject.workflow.api.dtos.EmployeeMapper;
import com.petproject.workflow.store.entities.Employee;
import com.petproject.workflow.store.repositories.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {
    private final S3Service s3Service;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public Iterable<EmployeeDto> getAllEmployees() {
        Iterable<Employee> employees = employeeRepository.findAll();
        return StreamSupport.stream(employees.spliterator(), false).map(
                employeeMapper::mapToEmployeeDto
        ).toList();
    }

    public Optional<EmployeeDto> getEmployeeById(UUID id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.map(employeeMapper::mapToEmployeeDto);
    }

    public EmployeeDto saveEmployee(EmployeeDto dto) {
        dto.setId(UUID.randomUUID());
        if (dto.getPosition().getId() == null) {
            dto.getPosition().setId(UUID.randomUUID());
        }
        Employee employee = employeeMapper.mapToEmployee(dto);
        employee = employeeRepository.save(employee);
        return employeeMapper.mapToEmployeeDto(employee);
    }

    public Iterable<EmployeeDto> getSubordinateEmployees(UUID id) {
        List<Employee> employees = employeeRepository.findAllEmployeesWithLowerPositionLevel(id);
        return employees.stream().map(employeeMapper::mapToEmployeeDto).toList();
    }

    public Iterable<EmployeeDto> getEmployeeWithPositionName(String positionName) {
        List<Employee> employees = employeeRepository.findAllEmployeesWithPositionName(positionName);
        return employees.stream().map(employeeMapper::mapToEmployeeDto).toList();
    }

    public Iterable<EmployeeDto> getBatchEmployees(Iterable<UUID> uuids) {
        List<Employee> employees = employeeRepository.findAllById(uuids);
        return employees.stream().map(employeeMapper::mapToEmployeeDto).toList();
    }

    public Iterable<EmployeeDto> getAllEmployeesByNameOrPositionName(String query) {
        List<Employee> employees = employeeRepository.findAllEmployeesByNameOrPositionName(query);
        return employees.stream().map(employeeMapper::mapToEmployeeDto).toList();
    }

    public Optional<S3Service.FileResponse> getEmployeePhoto(UUID id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.map(employee -> s3Service.getFile(employee.getPhotoKey()));
    }
}
