package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.StatementDto;
import com.petproject.workflow.api.exceptions.NotFoundIdException;
import com.petproject.workflow.api.services.StatementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public StatementDto createStatement(@RequestBody @Valid StatementDto statementDto) {
        return statementService.createStatement(statementDto);
    }

    @GetMapping("/{statementId}")
    public StatementDto getStatementById(@PathVariable UUID statementId) throws NotFoundIdException {
        return statementService.getStatementById(statementId);
    }
}
