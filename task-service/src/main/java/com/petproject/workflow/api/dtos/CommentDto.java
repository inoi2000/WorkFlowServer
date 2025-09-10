package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petproject.workflow.store.CommentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CommentDto {

    private UUID id;

    @NotNull
    @Size(min=5, message="Text must be at least 5 characters long")
    private String text;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate creation;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private CommentStatus commentStatus;

    private UUID taskId;
}