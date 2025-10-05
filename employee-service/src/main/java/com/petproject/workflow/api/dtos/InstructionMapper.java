package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.entities.Instruction;
import com.petproject.workflow.store.entities.InstructionConfirmation;
import com.petproject.workflow.store.repositories.InstructionRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InstructionMapper {

    private final InstructionConfirmationMapper instructionConfirmationMapper;

    public InstructionMapper(InstructionConfirmationMapper instructionConfirmationMapper) {
        this.instructionConfirmationMapper = instructionConfirmationMapper;
    }

    public Instruction maptoInstruction(InstructionDto instructionDto) {
        return new Instruction(
                instructionDto.getId(),
                instructionDto.getCreatedAt(),
                instructionDto.getUpdatedAt(),
                instructionDto.getValidUntil(),
                instructionDto.getInstructorId(),
                instructionDto.getData(),
                null
        );
    }

    public InstructionDto maptoInstructionDto(Instruction instruction) {
        List<InstructionConfirmationDto> instructionConfirmationDtos =
                instruction
                        .getInstructionConfirmations()
                        .stream()
                        .map(instructionConfirmationMapper::toInstructionConfirmationDto)
                        .toList();
        return new InstructionDto(
                instruction.getId(),
                instruction.getCreatedAt(),
                instruction.getUpdatedAt(),
                instruction.getStatus(),
                instruction.getInstructorId(),
                instruction.getData(),
                instruction.getValidUntil(),
                null,
                instructionConfirmationDtos
        );
    }

    public InstructionDto maptoInstructionDto(Instruction instruction, UUID employeeId) {
        InstructionConfirmationDto confirmationDto =
                instruction
                        .getInstructionConfirmations()
                        .stream()
                        .filter(confirmation ->
                                confirmation.getId().getEmployeeId().equals(employeeId))
                        .map(instructionConfirmationMapper::toInstructionConfirmationDto)
                        .findFirst()
                        .get();
        return new InstructionDto(
                instruction.getId(),
                instruction.getCreatedAt(),
                instruction.getUpdatedAt(),
                instruction.getStatus(),
                instruction.getInstructorId(),
                instruction.getData(),
                instruction.getValidUntil(),
                confirmationDto,
                null
        );
    }
}
