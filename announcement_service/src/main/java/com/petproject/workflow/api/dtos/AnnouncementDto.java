package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDto {

    private UUID id;

    @NotNull
    @Size(min=5, message="Title must be at least 5 characters long")
    private String title;

    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")

    private LocalDateTime createdAt;

    @NotNull
    @Size(min=10, message="Content must be at least 10 characters long")
    private String content;

    @JsonProperty("file_key")
    private FileKeyDto fileKey;
}
