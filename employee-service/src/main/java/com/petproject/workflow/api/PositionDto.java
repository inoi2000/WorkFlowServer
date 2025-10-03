package com.petproject.workflow.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionDto {
    private UUID id;
    @NotNull
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;
    @Min(0)
    private int level;
    @NotNull
    private boolean requiresSpecialDocuments;
}
