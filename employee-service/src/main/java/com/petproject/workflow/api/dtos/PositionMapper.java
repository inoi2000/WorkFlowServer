package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.entities.Position;
import org.springframework.stereotype.Component;

@Component
public class PositionMapper {

    public Position mapToPosition(PositionDto positionDto) {
        return new Position(
                positionDto.getId(),
                positionDto.getName(),
                positionDto.getLevel(),
                positionDto.isRequiresSpecialDocuments()
        );
    }

    public PositionDto mapToPositionDto(Position position) {
        return new PositionDto(
                position.getId(),
                position.getName(),
                position.getLevel(),
                position.isRequiresSpecialDocuments()
        );
    }
}