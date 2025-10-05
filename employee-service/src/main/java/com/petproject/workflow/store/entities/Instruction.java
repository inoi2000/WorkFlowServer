package com.petproject.workflow.store.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "instructions")
public class Instruction {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "valid_until")
    private LocalDate validUntil;

    @Column(name = "instructor_id")
    private UUID instructorId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "data_id")
    private InstructionData data;

    private transient boolean isConfirmed;
    private transient LocalDateTime confirmedAt;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "employee_instruction_statuses",
            joinColumns = @JoinColumn(name = "instruction_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees;

    @OneToMany(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "instruction_id")
    private List<EmployeeInstructionStatus> employeeInstructionStatus;

    private static final int NUMBER_OF_DAYS_UNTIL_INSTRUCTION_EXPIRES = 7;

    public Status getStatus() {
        if (validUntil == null) {
            return Status.VALID;
        } else if(LocalDate.now().isAfter(validUntil)) {
            return Status.EXPIRED;
        } else if (LocalDate.now().isAfter(validUntil.minusDays(NUMBER_OF_DAYS_UNTIL_INSTRUCTION_EXPIRES))) {
            return Status.EXPIRING;
        } else {
            return Status.VALID;
        }
    }
}
