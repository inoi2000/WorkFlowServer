package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.petproject.workflow.store.CommentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CommentDto {

    private UUID id;

    @NotNull
    @Size(min=5, message="Text must be at least 5 characters long")
    private String text;

    @NotNull
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull
    private CommentStatus commentStatus;

    @NotNull
    private UUID taskId;
}