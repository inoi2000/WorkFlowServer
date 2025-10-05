package com.petproject.workflow.store.repositories;

import com.petproject.workflow.store.entities.Instruction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InstructionRepository extends CrudRepository<Instruction, UUID> {

    @Query("SELECT i, ics.isConfirmed, ics.confirmedAt " +
            "FROM Instruction i " +
            "LEFT JOIN i.instructionConfirmations ics " +
            "WHERE i.id = :id")
    Optional<Instruction> findById(@Param("id") UUID id);

    @Query("SELECT i, ics.isConfirmed, ics.confirmedAt " +
            "FROM Instruction i " +
            "LEFT JOIN i.instructionConfirmations ics " +
            "WHERE ics.id.employeeId = :employeeId")
    List<Instruction> findAllByEmployeeId(@Param("employeeId") UUID employeeId);
}