package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.FileKey;
import org.springframework.stereotype.Component;

@Component
public class FileKeyMapper {

    public FileKey mapToFileKey(FileKeyDto dto) {
        return new FileKey(
                dto.getId(),
                dto.getKey(),
                dto.getCommentId()
        );
    }

    public FileKeyDto mapToFileKeyDto(FileKey fileKey) {
        return new FileKeyDto(
                fileKey.getId(),
                fileKey.getKey(),
                fileKey.getCommentId()
        );
    }
}
