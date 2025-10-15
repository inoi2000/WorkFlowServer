package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.StatementDto;
import com.petproject.workflow.api.exceptions.NotFoundIdException;
import com.petproject.workflow.api.services.StatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/statements", produces = "application/json")
@RequiredArgsConstructor
public class StatementController {

    private final StatementService statementService;

    @GetMapping("/")
    public Iterable<StatementDto> getAllStatements() {
        return statementService.getAllStatements();
    }

    @GetMapping("/{statementId}")
    public StatementDto getStatementById(@PathVariable UUID statementId) throws NotFoundIdException {
        return statementService.getStatementById(statementId);
    }
}
