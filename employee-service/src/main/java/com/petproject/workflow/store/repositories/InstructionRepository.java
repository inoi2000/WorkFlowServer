package com.petproject.workflow.store.repositories;

import com.petproject.workflow.store.entities.Instruction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InstructionRepository extends CrudRepository<Instruction, UUID> {

    @Query(value = "SELECT i.id, i.created_at as createdAt, i.updated_at as updatedAt, " +
            "i.valid_until as validUntil, i.instructor_id as instructorId, " +
            "d.id as data_id, d.text as data_text, " +
            "s.employee_id as employeeId, s.is_confirmed as isConfirmed, s.confirmed_at as confirmedAt " +
            "FROM instructions i " +
            "LEFT JOIN instructions_data d ON i.data_id = d.id " +
            "LEFT JOIN employee_instruction_statuses s ON s.instruction_id = i.id " +
            "WHERE i.id = :id", nativeQuery = true)
    Optional<Instruction> findById(@Param("id") UUID id);

    @Query(value = "SELECT i.id, i.created_at as createdAt, i.updated_at as updatedAt, " +
            "i.valid_until as validUntil, i.instructor_id as instructorId, " +
            "d.id as data_id, d.text as data_text, " +
            "s.employee_id as employeeId, s.is_confirmed as isConfirmed, s.confirmed_at as confirmedAt " +
            "FROM instructions i " +
            "LEFT JOIN instructions_data d ON i.data_id = d.id " +
            "LEFT JOIN employee_instruction_statuses s ON s.instruction_id = i.id " +
            "WHERE s.employee_id = :employeeId", nativeQuery = true)
    List<Instruction> findAllByEmployeeId(@Param("employeeId") UUID employeeId);
}