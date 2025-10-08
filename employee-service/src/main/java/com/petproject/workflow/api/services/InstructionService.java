package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.InstructionDto;
import com.petproject.workflow.api.dtos.InstructionMapper;
import com.petproject.workflow.store.entities.Instruction;
import com.petproject.workflow.store.repositories.InstructionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class InstructionService {

    private final InstructionRepository instructionRepository;
    private final InstructionMapper instructionMapper;

    @Autowired
    public InstructionService(
            InstructionRepository instructionRepository,
            InstructionMapper instructionMapper) {
        this.instructionRepository = instructionRepository;
        this.instructionMapper = instructionMapper;
    }

    public Optional<InstructionDto> getInstructionById(UUID id) {
        Optional<Instruction> instructionOptional = instructionRepository.findById(id);
        return instructionOptional.map(instructionMapper::maptoInstructionDto);
    }

    public List<InstructionDto> getAllInstructionsByEmployeeId(UUID employeeId) {
        return instructionRepository.findAllByEmployeeId(employeeId)
                .stream()
                .map(instruction -> instructionMapper.maptoInstructionDto(instruction, employeeId))
                .toList();
    }
}
