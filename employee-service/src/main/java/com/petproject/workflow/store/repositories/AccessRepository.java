package com.petproject.workflow.store.repositories;

import com.petproject.workflow.store.entities.Access;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccessRepository extends CrudRepository<Access, UUID> {
    Optional<Access> findById(UUID id);
}