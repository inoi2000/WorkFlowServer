package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.PositionDto;
import com.petproject.workflow.api.dtos.PositionMapper;
import com.petproject.workflow.store.entities.Position;
import com.petproject.workflow.store.repositories.PositionRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
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
        dto.setId(UUID.randomUUID());
        Position position = positionMapper.mapToPosition(dto);
        position = positionRepository.save(position);
        return positionMapper.mapToPositionDto(position);
    }

    @PatchMapping
    public PositionDto editPosition(@RequestBody @Valid PositionDto dto) {
        if(dto.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Position position = positionMapper.mapToPosition(dto);
        position = positionRepository.save(position);
        return positionMapper.mapToPositionDto(position);
    }
}
