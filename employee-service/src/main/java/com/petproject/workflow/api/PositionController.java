package com.petproject.workflow.api;

import com.petproject.workflow.store.Position;
import com.petproject.workflow.store.PositionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/positions", produces = "application/json")
public class PositionController {

    private final PositionRepository positionRepository;

    public PositionController(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @GetMapping("/")
    public Iterable<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Position savePosition(Position position) {
        return positionRepository.save(position);
    }
}
