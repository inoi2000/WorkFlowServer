package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.EmployeeDto;
import com.petproject.workflow.store.entities.Employee;
import com.petproject.workflow.store.entities.Instruction;
import com.petproject.workflow.store.repositories.InstructionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/instructions", produces="application/json")
public class InstructionController {

    private final InstructionRepository instructionRepository;

    public InstructionController(InstructionRepository instructionRepository) {
        this.instructionRepository = instructionRepository;
    }

    @GetMapping("/{id}")
    public Instruction getInstructionById(@PathVariable("id") UUID id) {
        var instruction = instructionRepository.findById(id).get();
        return instruction;
    }
}
