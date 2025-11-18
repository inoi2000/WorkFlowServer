package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.CommentDto;
import com.petproject.workflow.api.dtos.CommentMapper;
import com.petproject.workflow.store.Comment;
import com.petproject.workflow.store.CommentRepository;
import com.petproject.workflow.store.CommentStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentController commentController;

//    @Test
//    void getAllCommentsByTask_ShouldReturnComments_WhenValidTaskId() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        String taskIdString = taskId.toString();
//
//        Comment comment1 = new Comment(UUID.randomUUID(), "Comment 1", LocalDate.now(), CommentStatus.INFORMATION, taskId);
//        Comment comment2 = new Comment(UUID.randomUUID(), "Comment 2", LocalDate.now(), CommentStatus.FOR_APPROVAL, taskId);
//        List<Comment> comments = List.of(comment1, comment2);
//
//        CommentDto commentDto1 = new CommentDto(comment1.getId(), comment1.getText(), comment1.getCreation(), comment1.getCommentStatus(), comment1.getTaskId());
//        CommentDto commentDto2 = new CommentDto(comment2.getId(), comment2.getText(), comment2.getCreation(), comment2.getCommentStatus(), comment2.getTaskId());
//
//        when(commentRepository.findAllByTaskId(taskId)).thenReturn(comments);
//        when(commentMapper.mapToCommentDto(comment1)).thenReturn(commentDto1);
//        when(commentMapper.mapToCommentDto(comment2)).thenReturn(commentDto2);
//
//        // When
//        Iterable<CommentDto> result = commentController.getAllCommentsByTask(taskIdString);
//
//        // Then
//        assertNotNull(result);
//        List<CommentDto> resultList = ((List<CommentDto>) result);
//        assertEquals(2, resultList.size());
//        assertTrue(resultList.contains(commentDto1));
//        assertTrue(resultList.contains(commentDto2));
//
//        verify(commentRepository).findAllByTaskId(taskId);
//        verify(commentMapper, times(2)).mapToCommentDto(any(Comment.class));
//    }
//
//    @Test
//    void getAllCommentsByTask_ShouldReturnEmptyList_WhenNoCommentsFound() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        String taskIdString = taskId.toString();
//
//        when(commentRepository.findAllByTaskId(taskId)).thenReturn(List.of());
//
//        // When
//        Iterable<CommentDto> result = commentController.getAllCommentsByTask(taskIdString);
//
//        // Then
//        assertNotNull(result);
//        assertFalse(((List<CommentDto>) result).iterator().hasNext());
//
//        verify(commentRepository).findAllByTaskId(taskId);
//        verify(commentMapper, never()).mapToCommentDto(any(Comment.class));
//    }
//
//    @Test
//    void getAllCommentsByTask_ShouldThrowException_WhenInvalidTaskIdFormat() {
//        // Given
//        String invalidTaskId = "invalid-uuid-format";
//
//        // When & Then
//        assertThrows(IllegalArgumentException.class, () -> {
//            commentController.getAllCommentsByTask(invalidTaskId);
//        });
//
//        verify(commentRepository, never()).findAllByTaskId(any(UUID.class));
//        verify(commentMapper, never()).mapToCommentDto(any(Comment.class));
//    }
//
//    @Test
//    void getAllCommentsByTask_ShouldThrowException_WhenNullTaskId() {
//        // Given
//        String nullTaskId = null;
//
//        // When & Then
//        assertThrows(NullPointerException.class, () -> {
//            commentController.getAllCommentsByTask(nullTaskId);
//        });
//
//        verify(commentRepository, never()).findAllByTaskId(any(UUID.class));
//        verify(commentMapper, never()).mapToCommentDto(any(Comment.class));
//    }
//
//    @Test
//    void getAllCommentsByTask_ShouldThrowException_WhenEmptyTaskId() {
//        // Given
//        String emptyTaskId = "";
//
//        // When & Then
//        assertThrows(IllegalArgumentException.class, () -> {
//            commentController.getAllCommentsByTask(emptyTaskId);
//        });
//
//        verify(commentRepository, never()).findAllByTaskId(any(UUID.class));
//        verify(commentMapper, never()).mapToCommentDto(any(Comment.class));
//    }
//
//    @Test
//    void createComment_ShouldCreateComment_WhenValidDto() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//
//        CommentDto inputDto = new CommentDto(
//                null, // ID should be generated
//                "Valid comment text with sufficient length",
//                null, // Creation date should be set
//                CommentStatus.INFORMATION,
//                taskId
//        );
//
//        UUID generatedId = UUID.randomUUID();
//        CommentDto expectedDtoAfterProcessing = new CommentDto(
//                generatedId,
//                inputDto.getText(),
//                creationDate,
//                inputDto.getCommentStatus(),
//                inputDto.getTaskId()
//        );
//
//        Comment commentToSave = new Comment(
//                generatedId,
//                inputDto.getText(),
//                creationDate,
//                inputDto.getCommentStatus(),
//                inputDto.getTaskId()
//        );
//
//        Comment savedComment = new Comment(
//                generatedId,
//                inputDto.getText(),
//                creationDate,
//                inputDto.getCommentStatus(),
//                inputDto.getTaskId()
//        );
//
//        when(commentMapper.mapToComment(any(CommentDto.class))).thenReturn(commentToSave);
//        when(commentRepository.save(commentToSave)).thenReturn(savedComment);
//
//        // When
//        Comment result = commentController.createComment(inputDto);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(savedComment.getId(), result.getId());
//        assertEquals(inputDto.getText(), result.getText());
//        assertNotNull(result.getCreation()); // Creation date should be set
//        assertEquals(inputDto.getCommentStatus(), result.getCommentStatus());
//        assertEquals(inputDto.getTaskId(), result.getTaskId());
//
//        verify(commentMapper).mapToComment(any(CommentDto.class));
//        verify(commentRepository).save(commentToSave);
//    }
//
//    @Test
//    void createComment_ShouldSetIdAndCreationDate_WhenDtoHasNullValues() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//
//        CommentDto inputDto = new CommentDto(
//                null, // No ID
//                "Test comment text",
//                null, // No creation date
//                CommentStatus.FOR_APPROVAL,
//                taskId
//        );
//
//        UUID generatedId = UUID.randomUUID();
//        Comment commentToSave = new Comment(
//                generatedId,
//                inputDto.getText(),
//                LocalDate.now(),
//                inputDto.getCommentStatus(),
//                inputDto.getTaskId()
//        );
//
//        Comment savedComment = new Comment(
//                generatedId,
//                inputDto.getText(),
//                LocalDate.now(),
//                inputDto.getCommentStatus(),
//                inputDto.getTaskId()
//        );
//
//        when(commentMapper.mapToComment(any(CommentDto.class))).thenReturn(commentToSave);
//        when(commentRepository.save(commentToSave)).thenReturn(savedComment);
//
//        // When
//        Comment result = commentController.createComment(inputDto);
//
//        // Then
//        assertNotNull(result);
//        assertNotNull(result.getId()); // ID should be generated
//        assertNotNull(result.getCreation()); // Creation date should be set
//
//        verify(commentMapper).mapToComment(any(CommentDto.class));
//        verify(commentRepository).save(commentToSave);
//    }
//
//    @Test
//    void createComment_ShouldNotOverrideExistingId_WhenDtoHasId() {
//        // Given
//        UUID existingId = UUID.randomUUID();
//        UUID taskId = UUID.randomUUID();
//        LocalDate existingDate = LocalDate.now().minusDays(1);
//
//        CommentDto inputDto = new CommentDto(
//                existingId, // Existing ID
//                "Test comment with existing ID",
//                existingDate, // Existing creation date
//                CommentStatus.FOR_NOT_APPROVAL,
//                taskId
//        );
//
//        // The controller should override ID and creation date regardless of input
//        UUID newId = UUID.randomUUID();
//        LocalDate newDate = LocalDate.now();
//
//        Comment commentToSave = new Comment(
//                newId, // Should be overridden
//                inputDto.getText(),
//                newDate, // Should be overridden
//                inputDto.getCommentStatus(),
//                inputDto.getTaskId()
//        );
//
//        Comment savedComment = new Comment(
//                newId,
//                inputDto.getText(),
//                newDate,
//                inputDto.getCommentStatus(),
//                inputDto.getTaskId()
//        );
//
//        when(commentMapper.mapToComment(any(CommentDto.class))).thenReturn(commentToSave);
//        when(commentRepository.save(commentToSave)).thenReturn(savedComment);
//
//        // When
//        Comment result = commentController.createComment(inputDto);
//
//        // Then
//        assertNotNull(result);
//        // The result should have new ID and creation date, not the ones from input DTO
//        assertNotEquals(existingId, result.getId());
//        assertNotEquals(existingDate, result.getCreation());
//
//        verify(commentMapper).mapToComment(any(CommentDto.class));
//        verify(commentRepository).save(commentToSave);
//    }
//
//    @Test
//    void createComment_ShouldHandleNullDto() {
//        // Given
//        CommentDto nullDto = null;
//
//        // When & Then
//        assertThrows(NullPointerException.class, () -> {
//            commentController.createComment(nullDto);
//        });
//
//        verify(commentMapper, never()).mapToComment(any(CommentDto.class));
//        verify(commentRepository, never()).save(any(Comment.class));
//    }
//
//    @Test
//    void createComment_ShouldHandleDtoWithNullFields() {
//        // Given
//        CommentDto dtoWithNulls = new CommentDto(
//                null,
//                null, // Null text
//                null,
//                null, // Null status
//                null  // Null taskId
//        );
//
//        UUID generatedId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//
//        Comment commentToSave = new Comment(
//                generatedId,
//                null,
//                creationDate,
//                null,
//                null
//        );
//
//        Comment savedComment = new Comment(
//                generatedId,
//                null,
//                creationDate,
//                null,
//                null
//        );
//
//        when(commentMapper.mapToComment(any(CommentDto.class))).thenReturn(commentToSave);
//        when(commentRepository.save(commentToSave)).thenReturn(savedComment);
//
//        // When
//        Comment result = commentController.createComment(dtoWithNulls);
//
//        // Then
//        assertNotNull(result);
//        assertNotNull(result.getId()); // ID should be generated
//        assertNotNull(result.getCreation()); // Creation date should be set
//        assertNull(result.getText());
//        assertNull(result.getCommentStatus());
//        assertNull(result.getTaskId());
//
//        verify(commentMapper).mapToComment(any(CommentDto.class));
//        verify(commentRepository).save(commentToSave);
//    }
//
//    @Test
//    void createComment_ShouldHandleRepositoryException() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//
//        CommentDto inputDto = new CommentDto(
//                null,
//                "Test comment text",
//                null,
//                CommentStatus.INFORMATION,
//                taskId
//        );
//
//        UUID generatedId = UUID.randomUUID();
//        Comment commentToSave = new Comment(
//                generatedId,
//                inputDto.getText(),
//                LocalDate.now(),
//                inputDto.getCommentStatus(),
//                inputDto.getTaskId()
//        );
//
//        when(commentMapper.mapToComment(any(CommentDto.class))).thenReturn(commentToSave);
//        when(commentRepository.save(commentToSave)).thenThrow(new RuntimeException("Database error"));
//
//        // When & Then
//        assertThrows(RuntimeException.class, () -> {
//            commentController.createComment(inputDto);
//        });
//
//        verify(commentMapper).mapToComment(any(CommentDto.class));
//        verify(commentRepository).save(commentToSave);
//    }
}