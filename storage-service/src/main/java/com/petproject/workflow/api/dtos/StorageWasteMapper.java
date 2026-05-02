package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.entities.Storage;
import com.petproject.workflow.store.entities.Waste;
import org.springframework.stereotype.Component;

@Component
public class StorageWasteMapper {

    public Storage mapToStorage(StorageDto storageDto, boolean fullInfo) {
        Storage storage = new Storage(
                storageDto.getId(),
                storageDto.getName(),
                storageDto.getTotalVolume(),
                storageDto.getCurrentVolume(),
                mapToWaste(storageDto.getCurrentWaste(), false),
                null
        );
        if (fullInfo) {
            var allowedWastes = storageDto.getAllowedWastes().stream()
                    .map(waste -> mapToWaste(waste, false)).toList();
            storage.setAllowedWastes(allowedWastes);
        }
        return storage;
    }

    public StorageDto mapToStorageDto(Storage storage, boolean fullInfo) {
        StorageDto dto = new StorageDto(
                storage.getId(),
                storage.getName(),
                storage.getTotalVolume(),
                storage.getCurrentVolume(),
                mapToWasteDto(storage.getCurrentWaste(), false),
                null
        );
        if (fullInfo) {
            var allowedWastes = storage.getAllowedWastes().stream()
                    .map(waste -> mapToWasteDto(waste, false)).toList();
            dto.setAllowedWastes(allowedWastes);
        }
        return dto;
    }

    public Waste mapToWaste(WasteDto wasteDto, boolean fullInfo) {
        Waste waste = new Waste(
                wasteDto.getId(),
                wasteDto.getName(),
                wasteDto.isRecycle(),
                null
        );
        if (fullInfo) {
            var allowedStorage = wasteDto.getAllowedStorages().stream()
                    .map(storage -> mapToStorage(storage, false)).toList();
            waste.setAllowedStorages(allowedStorage);
        }
        return waste;
    }

    public WasteDto mapToWasteDto(Waste waste, boolean fullInfo) {
        WasteDto dto = new WasteDto(
                waste.getId(),
                waste.getName(),
                waste.isRecycle(),
                null
        );
        if (fullInfo) {
            var allowedStorage = waste.getAllowedStorages().stream()
                    .map(storage -> mapToStorageDto(storage, false)).toList();
            dto.setAllowedStorages(allowedStorage);
        }
        return dto;
    }
}