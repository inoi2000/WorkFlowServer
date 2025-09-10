package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.CommentDto;
import com.petproject.workflow.api.dtos.CommentMapper;
import com.petproject.workflow.store.Comment;
import com.petproject.workflow.store.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/comments", produces="application/json")
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentController(
            CommentRepository commentRepository,
            CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @GetMapping()
    public Iterable<CommentDto> getAllCommentsByTask(@RequestParam("task_id") String taskId) {
        UUID uuid = UUID.fromString(taskId);
        return commentRepository.findAllByTaskId(uuid).stream()
                .map(commentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@RequestBody CommentDto dto) {
        dto.setId(UUID.randomUUID());
        dto.setCreation(LocalDate.now());
        Comment comment = commentMapper.mapToComment(dto);
        return commentRepository.save(comment);
    }
}
