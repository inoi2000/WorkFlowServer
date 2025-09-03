package com.petproject.workflow.store;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface AnnouncementRepository extends CrudRepository<Announcement, UUID> {
    Optional<Announcement> findById(UUID id);
}