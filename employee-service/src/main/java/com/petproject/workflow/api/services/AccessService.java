package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.AccessDto;
import com.petproject.workflow.api.dtos.AccessMapper;
import com.petproject.workflow.store.entities.Access;
import com.petproject.workflow.store.repositories.AccessRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AccessService {

    private final AccessRepository accessRepository;
    private final AccessMapper accessMapper;

    @Autowired
    public AccessService(AccessRepository accessRepository, AccessMapper accessMapper) {
        this.accessRepository = accessRepository;
        this.accessMapper = accessMapper;
    }

    public Optional<AccessDto> getAccessById(UUID id) {
        Optional<Access> accessOptional = accessRepository.findById(id);
        return accessOptional.map(accessMapper::maptoAccessDto);
    }

    public List<AccessDto> getAllInstructionsByHolderId(UUID holderId) {
        return accessRepository.findByHolderId(holderId)
                .stream()
                .map(accessMapper::maptoAccessDto)
                .toList();
    }

    public List<AccessDto> getAllInstructionsByIssuerId(UUID issuerId) {
        return accessRepository.findByIssuerId(issuerId)
                .stream()
                .map(accessMapper::maptoAccessDto)
                .toList();
    }
}
