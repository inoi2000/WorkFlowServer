package com.petproject.workflow.api;

import com.petproject.workflow.store.Position;
import com.petproject.workflow.store.PositionRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/positions", produces = "application/json")
public class PositionController {

    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    public PositionController(PositionRepository positionRepository, PositionMapper positionMapper) {
        this.positionRepository = positionRepository;
        this.positionMapper = positionMapper;
    }

    @GetMapping("/")
    public Iterable<PositionDto> getAllPositions() {
        Iterable<Position> positionList = positionRepository.findAll();
        return StreamSupport.stream(positionList.spliterator(), false).map(
                positionMapper::mapToPositionDto
        ).toList();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public PositionDto savePosition(@RequestBody @Valid PositionDto dto) {
        Position position = positionMapper.mapToPosition(dto);
        position = positionRepository.save(position);
        return positionMapper.mapToPositionDto(position);
    }
}
