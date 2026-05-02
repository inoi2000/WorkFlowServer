package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.WasteDto;
import com.petproject.workflow.api.exceptions.NotFoundIdException;
import com.petproject.workflow.api.services.WasteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/wastes", produces = "application/json")
@RequiredArgsConstructor
public class WasteController {

    private final WasteService wasteService;

    @GetMapping("/")
    public Iterable<WasteDto> getAllWastes() {
        return wasteService.getAllWastes();
    }

    @GetMapping("/{wasteId}")
    public WasteDto getWasteById(@PathVariable UUID wasteId) throws NotFoundIdException {
        return wasteService.getWasteById(wasteId);
    }
}