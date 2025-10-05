package com.petproject.workflow.store.repositories;

import com.petproject.workflow.store.entities.InstructionConfirmation;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface InstructionConfirmationRepository extends CrudRepository<InstructionConfirmation, UUID> {
}
