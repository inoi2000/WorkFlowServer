package com.petproject.workflow.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class WasteDto {

    @NotNull
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    @JsonProperty("is_recycle")
    private boolean isRecycle;

    @NotNull
    @JsonProperty("allowed_storages")
    private List<StorageDto> allowedStorages = new ArrayList<>();
}
