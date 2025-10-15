package com.petproject.workflow.store.repositories;

import com.petproject.workflow.store.entities.Journey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface JourneyRepository extends CrudRepository<Journey, UUID> {

    List<Journey> findAll();

    List<Journey> findByDriverId(UUID driverId);

    List<Journey> findByCarId(UUID carId);
}
