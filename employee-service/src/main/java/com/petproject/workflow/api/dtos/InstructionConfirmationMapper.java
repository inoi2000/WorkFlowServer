package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.entities.InstructionConfirmation;
import org.springframework.stereotype.Component;

@Component
public class InstructionConfirmationMapper {

    public InstructionConfirmationDto toInstructionConfirmationDto(InstructionConfirmation instructionConfirmation) {
        return new InstructionConfirmationDto(
                instructionConfirmation.getId().getEmployeeId(),
                instructionConfirmation.isConfirmed(),
                instructionConfirmation.getConfirmedAt()
        );
    }
}
