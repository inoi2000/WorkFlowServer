package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.InstructionDto;
import com.petproject.workflow.api.dtos.InstructionMapper;
import com.petproject.workflow.api.services.InstructionService;
import com.petproject.workflow.store.repositories.InstructionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/instructions", produces = "application/json")
public class InstructionController {

    private final InstructionService instructionService;

    public InstructionController(
            InstructionService instructionService) {
        this.instructionService = instructionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructionDto> getInstructionById(@PathVariable("id") UUID id) {
        return instructionService.getInstructionById(id)
                .map(instruction -> new ResponseEntity<>(instruction, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/employee/{employeeId}")
    public Iterable<InstructionDto> getAllInstructionsByEmployeeId(@PathVariable("employeeId") UUID employeeId) {
        return instructionService.getAllInstructionsByEmployeeId(employeeId);
    }
}
