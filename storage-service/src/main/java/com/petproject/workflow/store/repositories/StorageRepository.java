package com.petproject.workflow.store.repositories;

import com.petproject.workflow.store.entities.Storage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StorageRepository extends CrudRepository<Storage, UUID> {
    List<Storage> findAll();

    Optional<Storage> findStorageById(UUID id);
}
