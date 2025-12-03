package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.CommentDto;
import com.petproject.workflow.api.dtos.CommentMapper;
import com.petproject.workflow.api.exceptions.CreateInstanceException;
import com.petproject.workflow.store.Comment;
import com.petproject.workflow.store.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final S3Service s3Service;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public Iterable<CommentDto> getAllCommentsByTask(UUID taskId) {
        return commentRepository.findAllByTaskId(taskId).stream()
                .map(commentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }

    public CommentDto createComment(CommentDto commentDto) throws CreateInstanceException {
        commentDto.setId(UUID.randomUUID());
        if (commentDto.getFileKey() != null && s3Service.fileExists(commentDto.getFileKey())) {
            Comment comment = commentMapper.mapToComment(commentDto);
            comment = commentRepository.save(comment);
            return commentMapper.mapToCommentDto(comment);
        } else  {
            throw new CreateInstanceException();
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        return s3Service.uploadFile(file, UUID.randomUUID().toString());
    }
}
