package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment mapToComment(CommentDto dto) {
        return new Comment(
                dto.getId(),
                dto.getText(),
                dto.getCreation(),
                dto.getCommentStatus(),
                dto.getTaskId()
        );
    }

    public CommentDto mapToCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getCreation(),
                comment.getCommentStatus(),
                comment.getTaskId()
        );
    }
}