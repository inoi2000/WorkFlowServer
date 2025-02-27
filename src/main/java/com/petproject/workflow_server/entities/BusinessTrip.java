package com.petproject.workflow_server.entities;

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
@Table(name = "business_trips")
public class BusinessTrip {
    @Id
    @Column(name = "id", columnDefinition = "BYNARY(16)")
    private UUID id;

    @Column(name = "start")
    private LocalDate start;

    @Column(name = "end")
    private LocalDate end;

    @Column(name = "place")
    private String place;
}
