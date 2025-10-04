package com.petproject.workflow.store.repositories;

import com.petproject.workflow.store.entities.Position;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PositionRepository extends CrudRepository<Position, UUID> {
    Optional<Position> findByName(String name);
}
