package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.CommentDto;
import com.petproject.workflow.api.dtos.CommentMapper;
import com.petproject.workflow.api.dtos.FileKeyDto;
import com.petproject.workflow.api.dtos.FileKeyMapper;
import com.petproject.workflow.api.exceptions.CreateInstanceException;
import com.petproject.workflow.store.Comment;
import com.petproject.workflow.store.CommentRepository;
import com.petproject.workflow.store.FileKey;
import com.petproject.workflow.store.FileKeyRepository;
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
    private final FileKeyRepository fileKeyRepository;
    private final CommentMapper commentMapper;
    private final FileKeyMapper fileKeyMapper;

    public Iterable<CommentDto> getAllCommentsByTask(UUID taskId) {
        return commentRepository.findAllByTaskId(taskId).stream()
                .map(commentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }

    public CommentDto createComment(CommentDto commentDto) {
        commentDto.setId(UUID.randomUUID());
        Comment comment = commentMapper.mapToComment(commentDto);
        comment = commentRepository.save(comment);
        return commentMapper.mapToCommentDto(comment);
    }

    public FileKeyDto uploadFile(MultipartFile file) throws IOException {
        String fileName = s3Service.uploadFile(file, UUID.randomUUID().toString());
        FileKey fileKey =  fileKeyRepository.save(new FileKey(
                UUID.randomUUID(),
                fileName,
                null
        ));
        return fileKeyMapper.mapToFileKeyDto(fileKey);
    }
}
