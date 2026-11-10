package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.petproject.workflow.store.entities.StoragePlacement;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageDto {

    @NotNull
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    @JsonProperty("total_volume")
    private double totalVolume;

    @NotNull
    @JsonProperty("current_volume")
    private double currentVolume;

    @JsonProperty("current_product")
    private ProductDto currentProduct;

    @NotNull
    @JsonProperty("allowed_mixing")
    private boolean allowedMixing;

    @JsonProperty("output_product")
    private ProductDto outputProduct;

    @NotNull
    private String sector;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private StoragePlacement placement;

    @NotNull
    private String location;

    @NotNull
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime updatedAt;

    @JsonProperty("allowed_products")
    private List<ProductDto> allowedProducts = new ArrayList<>();
}