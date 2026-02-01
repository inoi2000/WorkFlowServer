package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.TrailerDto;
import com.petproject.workflow.api.dtos.TrailerMapper;
import com.petproject.workflow.api.exceptions.NotFoundIdException;
import com.petproject.workflow.store.repositories.TrailerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/trailers", produces = "application/json")
@RequiredArgsConstructor
public class TrailerController {

    private final TrailerRepository trailerRepository;
    private final TrailerMapper trailerMapper;

    @GetMapping("/")
    public Iterable<TrailerDto> getAllTrailers() {
        return trailerRepository.findAll()
                .stream().map(trailerMapper::mapToTrailerDto).toList();
    }

    @GetMapping("/{trailerId}")
    public TrailerDto getTrailerById(UUID trailerId) throws NotFoundIdException {
        return trailerRepository.getTrailerById(trailerId)
                .map(trailerMapper::mapToTrailerDto)
                .orElseThrow(() -> new NotFoundIdException("Trailer with id " + trailerId + " not found"));

    }
}
