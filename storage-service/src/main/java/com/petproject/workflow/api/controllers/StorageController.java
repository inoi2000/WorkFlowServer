package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.StorageDto;
import com.petproject.workflow.api.exceptions.NotFoundIdException;
import com.petproject.workflow.api.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/storages", produces = "application/json")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @GetMapping("/")
    public Iterable<StorageDto> getAllStorages() {
        return storageService.getAllStorages();
    }

    @GetMapping("/{storageId}")
    public StorageDto getStorageById(@PathVariable UUID storageId) throws NotFoundIdException {
        return storageService.getStorageById(storageId);
    }
}
