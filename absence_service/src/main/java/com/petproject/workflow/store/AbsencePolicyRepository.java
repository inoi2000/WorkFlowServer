package com.petproject.workflow.store;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AbsencePolicyRepository extends CrudRepository<AbsencePolicy, UUID> {
}
