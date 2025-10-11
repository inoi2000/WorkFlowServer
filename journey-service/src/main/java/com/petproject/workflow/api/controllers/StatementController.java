package com.petproject.workflow.api.controllers;

import com.petproject.workflow.store.entities.Statement;
import com.petproject.workflow.store.repositories.StatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/statements", produces = "application/json")
public class StatementController {

    private final StatementRepository statementRepository;

    @Autowired
    public StatementController(StatementRepository statementRepository) {
        this.statementRepository = statementRepository;
    }

    @GetMapping("/")
    public Iterable<Statement> getAllStatements() {
        return statementRepository.findAll();
    }

    @GetMapping("/{id}")
    public Statement getStatementById(@PathVariable UUID id) {
        return statementRepository.findById(id).orElse(null);
    }
}
