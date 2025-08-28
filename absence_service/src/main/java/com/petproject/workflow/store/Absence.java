package com.petproject.workflow.store;

import jakarta.persistence.*;
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
@Table(name = "absences")
public class Absence {
    @Id
    @Column(name = "id", columnDefinition = "BYNARY(16)")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AbsenceType type;

    @Enumerated(EnumType.STRING)
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

    @Column(name = "employee_id", columnDefinition = "BYNARY(16)")
    private UUID employeeId;
}