package com.petproject.workflow.store.repositories;

import com.petproject.workflow.store.entities.Statement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface StatementRepository extends CrudRepository<Statement, UUID> {

    List<Statement> findAll();
}
