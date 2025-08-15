package com.petproject.workflow.store;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CommentRepository extends CrudRepository<Comment, UUID> {
    @Query("SELECT c FROM Comment c WHERE c.task = (SELECT task FROM Task task WHERE task.id = :taskId)")
    Iterable<Comment> findAllByTaskId(UUID taskId);
}
