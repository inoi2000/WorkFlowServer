package com.petproject.workflow.api;

import com.petproject.workflow.store.Announcement;
import com.petproject.workflow.store.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/announcements", produces = "application/json")
public class AnnouncementController {
    private final AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementController(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @GetMapping()
    public Iterable<Announcement> findAll() {
        return announcementRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Announcement> findById(@PathVariable UUID id) {
        Optional<Announcement> optionalAnnouncement = announcementRepository.findById(id);
        return optionalAnnouncement
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Announcement create(@RequestBody Announcement announcement) {
        announcement.setId(UUID.randomUUID());
        return announcementRepository.save(announcement);
    }
}
