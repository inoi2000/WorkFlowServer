package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.entities.StorageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorageEventMapper {

    private final StorageProductMapper storageProductMapper;

    public StorageEvent mapToStorageEvent(StorageEventDto storageEventDto) {
        var storage = storageProductMapper.mapToStorage(storageEventDto.getStorage(), false);
        var product = storageProductMapper.mapToProduct(storageEventDto.getProduct(), false);

        return new StorageEvent(
                storageEventDto.getId(),
                storageEventDto.getActionType(),
                storageEventDto.getVolume(),
                storage,
                product,
                storageEventDto.getEmployeeId(),
                storageEventDto.getCreatedAt()
        );
    }

    public StorageEventDto mapToStorageEventDto(StorageEvent storageEvent) {
        var storageDto = storageProductMapper.mapToStorageDto(storageEvent.getStorage(), false);
        var productDto = storageProductMapper.mapToProductDto(storageEvent.getProduct(), false);

        return new StorageEventDto(
                storageEvent.getId(),
                storageEvent.getActionType(),
                storageEvent.getVolume(),
                storageDto,
                productDto,
                storageEvent.getEmployeeId(),
                storageEvent.getCreatedAt()
        );
    }
}