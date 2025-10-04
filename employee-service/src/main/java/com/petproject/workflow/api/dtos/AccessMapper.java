package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.entities.Access;
import org.springframework.stereotype.Component;

@Component
public class AccessMapper {

    public Access maptoAccess(AccessDto accessDto) {
        return new Access(
                accessDto.getId(),
                accessDto.getCreatedAt(),
                accessDto.getUpdatedAt(),
                accessDto.getType(),
                accessDto.getValidUntil(),
                accessDto.getData(),
                accessDto.getIssuerId(),
                accessDto.getHolderId()
        );
    }

    public AccessDto maptoAccessDto(Access access) {
        return new AccessDto(
                access.getId(),
                access.getCreatedAt(),
                access.getUpdatedAt(),
                access.getType(),
                access.getStatus(),
                access.getIssuerId(),
                access.getHolderId(),
                access.getData(),
                access.getValidUntil()
        );
    }
}
