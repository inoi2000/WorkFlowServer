package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.entities.StorageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorageEventMapper {

    private final StorageWasteMapper storageWasteMapper;

    public StorageEvent mapToStorageEvent(StorageEventDto storageEventDto) {
        return new StorageEvent(
                storageEventDto.getId(),
                storageEventDto.getActionType(),
                storageEventDto.getVolume(),
                storageWasteMapper.mapToStorage(storageEventDto.getStorage(), false),
                storageWasteMapper.mapToWaste(storageEventDto.getWaste(), false)
        );
    }

    public StorageEventDto mapToStorageEventDto(StorageEvent storageEvent) {
        return new StorageEventDto(
                storageEvent.getId(),
                storageEvent.getActionType(),
                storageEvent.getVolume(),
                storageWasteMapper.mapToStorageDto(storageEvent.getStorage(), false),
                storageWasteMapper.mapToWasteDto(storageEvent.getWaste(), false)
        );
    }
}