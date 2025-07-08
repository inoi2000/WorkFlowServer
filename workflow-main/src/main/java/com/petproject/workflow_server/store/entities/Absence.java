package com.petproject.workflow_server.store.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "absences")
public class Absence {
    @Id
    @Column(name = "id", columnDefinition = "BYNARY(16)")
    private UUID id;

    @Column(name = "type")
    private AbsenceType type;

    @Column(name = "status")
    private AbsenceStatus status;

    @Column(name = "start")
    private LocalDate start;

    @Column(name = "end")
    private LocalDate end;

    @Column(name = "place")
    private String place;

    @Column(name = "is_approval")
    private boolean isApproval;
}