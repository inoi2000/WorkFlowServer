package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.StorageWasteMapper;
import com.petproject.workflow.api.dtos.WasteDto;
import com.petproject.workflow.api.exceptions.NotFoundIdException;
import com.petproject.workflow.store.entities.Waste;
import com.petproject.workflow.store.repositories.WasteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WasteService {

    private final WasteRepository wasteRepository;
    private final StorageWasteMapper storageWasteMapper;

    public List<WasteDto> getAllWastes() {
        List<Waste> wastes = wasteRepository.findAll();
        return wastes.stream()
                .map(waste -> storageWasteMapper.mapToWasteDto(waste, false))
                .toList();
    }

    public WasteDto getWasteById(UUID wasteId) throws NotFoundIdException {
        Waste waste = wasteRepository
                .findById(wasteId)
                .orElseThrow(() -> new NotFoundIdException("Waste with id " + wasteId + " not found"));
        return storageWasteMapper.mapToWasteDto(waste, true);
    }
}