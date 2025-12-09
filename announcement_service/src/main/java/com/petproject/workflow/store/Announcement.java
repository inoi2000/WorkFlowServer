package com.petproject.workflow.store;

import jakarta.persistence.*;
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

    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "content")
    private String content;

    @OneToOne(cascade = {CascadeType.MERGE,  CascadeType.PERSIST, CascadeType.REFRESH})
    private FileKey fileKey;
}
