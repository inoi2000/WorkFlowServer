package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.AnnouncementDto;
import com.petproject.workflow.api.dtos.FileKeyDto;
import com.petproject.workflow.api.exceptions.CreateInstanceException;
import com.petproject.workflow.api.services.AnnouncementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/announcements", produces = "application/json")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping()
    public Iterable<AnnouncementDto> findAll() {
        return announcementService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementDto> findById(@PathVariable UUID id) {
        Optional<AnnouncementDto> optionalAnnouncement = announcementService.findById(id);
        return optionalAnnouncement
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public AnnouncementDto create(@RequestBody @Valid AnnouncementDto dto) {
        return announcementService.create(dto);
    }

    // Work with posters
    @GetMapping("/{id}/poster")
    public ResponseEntity<byte[]> downloadFile(@PathVariable UUID id) {
        return announcementService.getAnnouncementPoster(id)
                .map(fileResponse -> ResponseEntity.ok()
                        .contentType(fileResponse.getContentType())
                        .body(fileResponse.getData()))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/posters/upload")
    public ResponseEntity<FileKeyDto> uploadFile(
            @RequestParam("file") MultipartFile file
    ) throws CreateInstanceException {
        try {
            FileKeyDto dto = announcementService.uploadFile(file);
            return ResponseEntity.ok(dto);
        } catch (IOException e) {
            throw new CreateInstanceException(e.getMessage());
        }
    }
}
