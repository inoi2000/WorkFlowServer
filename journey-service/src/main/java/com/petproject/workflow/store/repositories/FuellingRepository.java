package com.petproject.workflow.store.repositories;

import com.petproject.workflow.store.entities.Fuelling;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface FuellingRepository extends CrudRepository<Fuelling, UUID> {
    List<Fuelling> findAll();
}
