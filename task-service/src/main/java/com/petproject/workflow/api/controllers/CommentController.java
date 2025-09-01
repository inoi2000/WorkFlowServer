package com.petproject.workflow.api.controllers;

import com.petproject.workflow.store.Comment;
import com.petproject.workflow.store.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/comments", produces="application/json")
public class CommentController {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping()
    public Iterable<Comment> getAllCommentsByTask(@RequestParam("task_id") String taskId) {
        UUID uuid = UUID.fromString(taskId);
        return commentRepository.findAllByTaskId(uuid);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@RequestBody Comment comment) {
        comment.setId(UUID.randomUUID());
        return commentRepository.save(comment);
    }
}
