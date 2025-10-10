package com.petproject.workflow.store.repositories;

import com.petproject.workflow.store.entities.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CarRepository extends CrudRepository<Car, UUID> {
}
