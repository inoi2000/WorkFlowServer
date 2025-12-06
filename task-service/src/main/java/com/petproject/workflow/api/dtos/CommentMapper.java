package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.Comment;
import com.petproject.workflow.store.FileKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final FileKeyMapper fileKeyMapper;

    public Comment mapToComment(CommentDto dto) {
        List<FileKey> fileKeys = new ArrayList<>();
        if (dto.getFileKeys() != null) {
            fileKeys = dto.getFileKeys().stream().map(fileKeyMapper::mapToFileKey).toList();
        }
        return new Comment(
                dto.getId(),
                dto.getText(),
                dto.getCreatedAt(),
                dto.getCommentStatus(),
                fileKeys,
                dto.getTaskId()
        );
    }

    public CommentDto mapToCommentDto(Comment comment) {
        List<FileKeyDto> fileKeysDto = new ArrayList<>();
        if (comment.getFileKeys() != null) {
            fileKeysDto = comment.getFileKeys().stream().map(fileKeyMapper::mapToFileKeyDto).toList();
        }
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getCreatedAt(),
                comment.getCommentStatus(),
                fileKeysDto,
                comment.getTaskId()
        );
    }
}