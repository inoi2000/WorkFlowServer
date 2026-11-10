package com.petproject.workflow.store.repositories;

import com.petproject.workflow.store.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {
    List<Product> findAll();

    Optional<Product> findById(UUID id);
}