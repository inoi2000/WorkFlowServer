package com.petproject.workflow.store;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PositionRepository extends CrudRepository<Position, UUID> {
    Optional<Position> findByName(String name);
}
