package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.Comment;
import com.petproject.workflow.store.Task;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TaskMapper {

    private final CommentMapper commentMapper;

    public TaskMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public Task mapToTask(TaskDto dto) {

        List<Comment> comments;
        if (dto.getComments() != null) {
            comments = dto.getComments().stream().map(
                    commentMapper::mapToComment
            ).toList();
        } else {
            comments = Collections.emptyList();
        }

        return new Task(dto.getId(),
                dto.getDescription(),
                dto.getStatus(),
                dto.getPriority(),
                dto.getCreation(),
                dto.getDeadline(),
                dto.getDestination(),
                dto.isShouldBeInspected(),
                dto.getExecutor().getId(),
                dto.getInspector().getId(),
                comments
        );
    }

    public TaskDto mapToTaskDto(Task task, EmployeeDto executor, EmployeeDto inspector) {

        List<CommentDto> comments;
        if (task.getComments() != null) {
            comments = task.getComments().stream().map(
                    commentMapper::mapToCommentDto
            ).toList();
        } else {
            comments = Collections.emptyList();
        }

        return new TaskDto(
                task.getId(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getCreation(),
                task.getDeadline(),
                task.getDestination(),
                task.isShouldBeInspected(),
                executor,
                inspector,
                comments
        );
    }
}
