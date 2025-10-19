package com.petproject.workflow.store;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "announcements")
public class Announcement {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @NotNull
    @Size(min=5, message="Title must be at least 5 characters long")
    @Column(name = "title")
    private String title;

    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotNull
    @Size(min=10, message="Content must be at least 10 characters long")
    @Column(name = "content")
    private String content;

    @Column(name = "img_url")
    private String imgUrl;
}
