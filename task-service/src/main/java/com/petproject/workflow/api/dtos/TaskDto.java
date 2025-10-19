package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.petproject.workflow.store.Comment;
import com.petproject.workflow.store.TaskPriority;
import com.petproject.workflow.store.TaskStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    @NotNull
    private UUID id;

    @NotNull
    @Size(min=5, message="Description must be at least 5 characters long")
    private String description;

    @NotNull
    private TaskStatus status;

    @NotNull
    private TaskPriority priority;

    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime createdAt;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    @NotNull
    private boolean shouldBeInspected;

    private EmployeeDto executor;

    private EmployeeDto inspector;

    private List<CommentDto> comments;
}
