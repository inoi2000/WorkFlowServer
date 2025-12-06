package com.petproject.workflow.store;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface FileKeyRepository extends CrudRepository<FileKey, UUID> {
    List<FileKey> findByCommentId(UUID commentId);
}
