package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.StorageDto;
import com.petproject.workflow.api.dtos.StorageProductMapper;
import com.petproject.workflow.api.exceptions.NotFoundIdException;
import com.petproject.workflow.store.entities.Storage;
import com.petproject.workflow.store.repositories.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final StorageRepository storageRepository;
    private final StorageProductMapper storageProductMapper;

    public List<StorageDto> getAllStorages() {
        List<Storage> storages = storageRepository.findAll();
        return storages.stream()
                .map(storage -> storageProductMapper.mapToStorageDto(storage, false))
                .toList();
    }

    public StorageDto getStorageById(UUID storageId) throws NotFoundIdException {
        Storage storage = storageRepository
                .findById(storageId)
                .orElseThrow(() -> new NotFoundIdException("Storage with id " + storageId + " not found"));
        return storageProductMapper.mapToStorageDto(storage, true);
    }
}