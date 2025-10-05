package com.petproject.workflow.store.repositories;

import com.petproject.workflow.store.entities.EmployeeInstructionStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EmployeeInstructionStatusRepository extends CrudRepository<EmployeeInstructionStatus, UUID> {
}
