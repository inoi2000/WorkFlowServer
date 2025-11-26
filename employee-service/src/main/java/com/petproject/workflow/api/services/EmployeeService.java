package com.petproject.workflow.api.services;

import com.petproject.workflow.store.entities.Employee;
import com.petproject.workflow.store.repositories.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {
    private final S3Service s3Service;
    private final EmployeeRepository employeeRepository;

    public Optional<S3Service.FileResponse> getEmployeePhoto(UUID id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.map(employee -> s3Service.getFile(employee.getPhotoKey()));
    }
}
