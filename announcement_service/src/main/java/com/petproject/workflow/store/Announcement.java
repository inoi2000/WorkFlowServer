package com.petproject.workflow.store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "announcements")
public class Announcement {
    @Id
    @Column(name = "id", columnDefinition = "BYNARY(16)")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "post_data")
    private LocalDate postData;

    @Column(name = "content")
    private String content;

    @Column(name = "img_url")
    private String imgUrl;
}
