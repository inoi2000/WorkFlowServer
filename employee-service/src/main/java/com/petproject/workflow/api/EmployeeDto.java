package com.petproject.workflow.api;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private UUID id;
    @NotNull
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;
    @NotNull
    @Size(min = 12, max = 12, message="Phone must 12 characters long")
    private String phone;
    @NotNull
    private PositionDto position;
    private DepartmentDto department;
}