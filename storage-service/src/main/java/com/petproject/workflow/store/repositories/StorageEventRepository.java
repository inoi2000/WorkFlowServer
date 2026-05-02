package com.petproject.workflow.store.repositories;

import com.petproject.workflow.store.entities.StorageEvent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StorageEventRepository extends CrudRepository<StorageEvent, UUID> {
    List<StorageEvent> findAll();

    Optional<StorageEvent> findById(UUID id);
}
