package com.petproject.workflow.store.repositories;

import com.petproject.workflow.api.dtos.CarDto;
import com.petproject.workflow.store.entities.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarRepository extends CrudRepository<Car, UUID> {
    List<Car> findAll();

    Optional<Car> getCarById(UUID id);
}
