package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.Announcement;
import com.petproject.workflow.store.FileKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnnouncementMapper {

    private final FileKeyMapper fileKeyMapper;

    public Announcement mapToAnnouncement(AnnouncementDto dto) {
        FileKey fileKey = null;
        if (dto.getFileKey() != null) {
            fileKey = fileKeyMapper.mapToFileKey(dto.getFileKey());
        }
        return new Announcement(
                dto.getId(),
                dto.getTitle(),
                dto.getCreatedAt(),
                dto.getContent(),
                fileKey
        );
    }

    public AnnouncementDto mapToAnnouncementDto(Announcement announcement) {
        FileKeyDto fileKeyDto = null;
        if (announcement.getFileKey() != null) {
            fileKeyDto = fileKeyMapper.mapToFileKeyDto(announcement.getFileKey());
        }
        return new AnnouncementDto(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getCreatedAt(),
                announcement.getContent(),
                fileKeyDto
        );
    }
}
