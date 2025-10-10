package com.petproject.workflow.store.repositories;

import com.petproject.workflow.store.entities.Journey;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface JourneyRepository extends CrudRepository<Journey, UUID> {
}
