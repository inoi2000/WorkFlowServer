package com.petproject.workflow.store;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AbsenceRepository extends CrudRepository<Absence, UUID> {
    Optional<Absence> findById(UUID id);
    List<Absence> findAllByEmployeeId(UUID employeeId);
}
