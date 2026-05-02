package com.petproject.workflow.store.repositories;

import com.petproject.workflow.store.entities.Waste;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WasteRepository extends CrudRepository<Waste, UUID> {
    List<Waste> findAll();

    Optional<Waste> findById(UUID id);
}