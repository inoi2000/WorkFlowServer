package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.AnnouncementDto;
import com.petproject.workflow.api.dtos.AnnouncementMapper;
import com.petproject.workflow.api.dtos.FileKeyDto;
import com.petproject.workflow.api.dtos.FileKeyMapper;
import com.petproject.workflow.store.Announcement;
import com.petproject.workflow.store.AnnouncementRepository;
import com.petproject.workflow.store.FileKey;
import com.petproject.workflow.store.FileKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper;
    private final FileKeyRepository fileKeyRepository;
    private final FileKeyMapper fileKeyMapper;
    private final S3Service s3Service;

    public Iterable<AnnouncementDto> findAll() {
        return announcementRepository
                .findAll()
                .stream()
                .map(announcementMapper::mapToAnnouncementDto)
                .toList();
    }

    public Optional<AnnouncementDto> findById(UUID id) {
        Optional<Announcement> optionalAnnouncement = announcementRepository.findById(id);
        return optionalAnnouncement.map(announcementMapper::mapToAnnouncementDto);
    }

    public AnnouncementDto create(AnnouncementDto dto) {
        dto.setId(UUID.randomUUID());
        dto.setCreatedAt(LocalDateTime.now());
        Announcement announcement = announcementMapper.mapToAnnouncement(dto);
        return announcementMapper.mapToAnnouncementDto(
                announcementRepository.save(announcement));
    }

    public Optional<S3Service.FileResponse> getAnnouncementPoster(UUID id) {
        Optional<Announcement> optionalAnnouncement = announcementRepository.findById(id);
        return optionalAnnouncement.map(announcement -> {
            try {
                if (announcement.getFileKey() != null) {
                    return s3Service.getFile(announcement.getFileKey().getKey());
                } else {
                    return s3Service.getFile("default.jpeg");
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public FileKeyDto uploadFile(MultipartFile file) throws IOException {
        String fileName = s3Service.uploadFile(file, UUID.randomUUID().toString());
        FileKey fileKey =  fileKeyRepository.save(new FileKey(
                UUID.randomUUID(),
                fileName
        ));
        return fileKeyMapper.mapToFileKeyDto(fileKey);
    }
}
