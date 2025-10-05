package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.InstructionDto;
import com.petproject.workflow.api.dtos.InstructionMapper;
import com.petproject.workflow.store.repositories.InstructionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/instructions", produces = "application/json")
public class InstructionController {

    private final InstructionRepository instructionRepository;
    private final InstructionMapper instructionMapper;

    public InstructionController(
            InstructionRepository instructionRepository,
            InstructionMapper instructionMapper) {
        this.instructionRepository = instructionRepository;
        this.instructionMapper = instructionMapper;
    }

    @GetMapping("/{id}")
    public InstructionDto getInstructionById(@PathVariable("id") UUID id) {
        var instruction = instructionRepository.findById(id).get();
        return instructionMapper.maptoInstructionDto(instruction);
    }

    @GetMapping("a/{id}")
    public List<InstructionDto> getAllInstructionById(@PathVariable("id") UUID id) {
        return instructionRepository.findAllByEmployeeId(id)
                .stream()
                .map(instruction -> instructionMapper.maptoInstructionDto(instruction, id))
                .toList();
    }
}
