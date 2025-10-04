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
public class DepartmentDto {
    private UUID id;
    @NotNull
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;
}
