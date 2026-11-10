package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.petproject.workflow.store.entities.ProductStatus;
import com.petproject.workflow.store.entities.ProductType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @NotNull
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private double density;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ProductStatus status;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ProductType type;

    @NotNull
    @JsonProperty("allowed_storages")
    private List<StorageDto> allowedStorages = new ArrayList<>();
}