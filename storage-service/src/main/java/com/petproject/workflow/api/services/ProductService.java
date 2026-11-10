package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.StorageProductMapper;
import com.petproject.workflow.api.dtos.ProductDto;
import com.petproject.workflow.api.exceptions.NotFoundIdException;
import com.petproject.workflow.store.entities.Product;
import com.petproject.workflow.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StorageProductMapper storageProductMapper;

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product ->
                        storageProductMapper.mapToProductDto(product, false))
                .toList();
    }

    public ProductDto getProductById(UUID productId) throws NotFoundIdException {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new NotFoundIdException("Petroleum product with id " + productId + " not found"));
        return storageProductMapper.mapToProductDto(product, true);
    }
}