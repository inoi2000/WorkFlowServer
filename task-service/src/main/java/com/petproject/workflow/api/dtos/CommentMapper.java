package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment mapToComment(CommentDto dto) {
        return new Comment(
                dto.getId(),
                dto.getText(),
                dto.getFileKey(),
                dto.getCreatedAt(),
                dto.getCommentStatus(),
                dto.getTaskId()
        );
    }

    public CommentDto mapToCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getFileKey(),
                comment.getCreatedAt(),
                comment.getCommentStatus(),
                comment.getTaskId()
        );
    }
}