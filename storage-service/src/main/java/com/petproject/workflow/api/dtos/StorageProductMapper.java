package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.entities.Product;
import com.petproject.workflow.store.entities.Storage;
import com.petproject.workflow.store.entities.StorageLocation;
import com.petproject.workflow.store.entities.StorageSector;
import org.springframework.stereotype.Component;

@Component
public class StorageProductMapper {

    public Storage mapToStorage(StorageDto storageDto, boolean fullInfo) {
        if (storageDto == null) {
            return null;
        }

        var currentProduct = mapToProduct(storageDto.getCurrentProduct(), false);
        var outputProduct = mapToProduct(storageDto.getOutputProduct(), false);

        Storage storage = new Storage(
                storageDto.getId(),
                storageDto.getName(),
                storageDto.getTotalVolume(),
                storageDto.getCurrentVolume(),
                currentProduct,
                storageDto.isAllowedMixing(),
                outputProduct,
                StorageSector.fromString(storageDto.getSector()),
                storageDto.getPlacement(),
                StorageLocation.fromString(storageDto.getLocation()),
                storageDto.getUpdatedAt(),
                null
        );
        if (fullInfo) {
            var allowedProducts = storageDto.getAllowedProducts().stream()
                    .map(product -> mapToProduct(product, false))
                    .toList();
            storage.setAllowedProducts(allowedProducts);
        }
        return storage;
    }

    public StorageDto mapToStorageDto(Storage storage, boolean fullInfo) {
        if (storage == null) {
            return null;
        }

        var currentProductDto = mapToProductDto(storage.getCurrentProduct(), false);
        var outputProductDto = mapToProductDto(storage.getOutputProduct(), false);

        StorageDto dto = new StorageDto(
                storage.getId(),
                storage.getName(),
                storage.getTotalVolume(),
                storage.getCurrentVolume(),
                currentProductDto,
                storage.isAllowedMixing(),
                outputProductDto,
                storage.getSector().toString(),
                storage.getPlacement(),
                storage.getLocation().toString(),
                storage.getUpdatedAt(),
                null
        );
        if (fullInfo) {
            var allowedProducts = storage.getAllowedProducts().stream()
                    .map(product -> mapToProductDto(product, false))
                    .toList();
            dto.setAllowedProducts(allowedProducts);
        }
        return dto;
    }

    public Product mapToProduct(ProductDto productDto, boolean fullInfo) {
        if (productDto == null) {
            return null;
        }

        Product product = new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getDensity(),
                productDto.getStatus(),
                productDto.getType(),
                null
        );
        if (fullInfo) {
            var allowedStorage = productDto.getAllowedStorages().stream()
                    .map(storage -> mapToStorage(storage, false)).toList();
            product.setAllowedStorages(allowedStorage);
        }
        return product;
    }

    public ProductDto mapToProductDto(Product product, boolean fullInfo) {
        if (product == null) {
            return null;
        }

        ProductDto dto = new ProductDto(
                product.getId(),
                product.getName(),
                product.getDensity(),
                product.getStatus(),
                product.getType(),
                null
        );
        if (fullInfo) {
            var allowedStorage = product.getAllowedStorages().stream()
                    .map(storage -> mapToStorageDto(storage, false)).toList();
            dto.setAllowedStorages(allowedStorage);
        }
        return dto;
    }
}