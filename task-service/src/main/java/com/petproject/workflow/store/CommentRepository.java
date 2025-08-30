package com.petproject.workflow.store;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CommentRepository extends CrudRepository<Comment, UUID> {
    Iterable<Comment> findAllByTaskId(UUID taskId);
}