package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.CommentDto;
import com.petproject.workflow.api.exceptions.CreateInstanceException;
import com.petproject.workflow.api.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/comments", produces = "application/json")
public class CommentController {

    private final CommentService commentService;

    @GetMapping()
    public Iterable<CommentDto> getAllCommentsByTask(@RequestParam("task_id") UUID taskId) {
        return commentService.getAllCommentsByTask(taskId);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@RequestBody @Valid CommentDto dto) throws CreateInstanceException {
        return commentService.createComment(dto);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String fileKey = commentService.uploadFile(file);
            return ResponseEntity.ok(fileKey);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при загрузке файла: " + e.getMessage());
        }
    }
}
