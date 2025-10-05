package com.petproject.workflow.store.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee_instruction_statuses")
public class EmployeeInstructionStatus {

    @EmbeddedId
    private EmployeeInstructionKey id;

    @Column(name = "is_confirmed")
    private boolean isConfirmed;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;
}
