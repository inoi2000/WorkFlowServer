package com.petproject.workflow.store.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInstructionKey implements Serializable {

    @Column(name = "employee_id", columnDefinition = "BINARY(16)")
    private UUID employeeId;

    @Column(name = "instruction_id", columnDefinition = "BINARY(16)")
    private UUID instructionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeInstructionKey that = (EmployeeInstructionKey) o;
        return Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(instructionId, that.instructionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, instructionId);
    }
}
