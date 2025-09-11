package com.petproject.workflow.api;

import com.petproject.workflow.store.Position;
import org.springframework.stereotype.Component;

@Component
public class PositionMapper {

    public Position mapToPosition(PositionDto positionDto) {
        return new Position(
                positionDto.getId(),
                positionDto.getName(),
                positionDto.getLevel()
        );
    }

    public PositionDto mapToPositionDto(Position position) {
        return new PositionDto(
                position.getId(),
                position.getName(),
                position.getLevel()
        );
    }
}