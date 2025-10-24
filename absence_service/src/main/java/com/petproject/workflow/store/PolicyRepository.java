package com.petproject.workflow.store;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PolicyRepository extends CrudRepository<Policy, UUID> {
}
