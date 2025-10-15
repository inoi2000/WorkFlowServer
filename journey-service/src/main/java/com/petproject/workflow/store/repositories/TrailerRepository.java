package com.petproject.workflow.store.repositories;

import com.petproject.workflow.store.entities.Trailer;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TrailerRepository extends CrudRepository<Trailer, UUID> {
}
