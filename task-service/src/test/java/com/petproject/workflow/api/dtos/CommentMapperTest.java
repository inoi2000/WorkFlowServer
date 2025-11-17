package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.Comment;
import com.petproject.workflow.store.CommentStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentMapperTest {

    @InjectMocks
    private CommentMapper commentMapper;

//    @Test
//    void mapToComment_ShouldMapAllFieldsCorrectly() {
//        // Given
//        UUID id = UUID.randomUUID();
//        UUID taskId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//
//        CommentDto dto = new CommentDto(
//                id,
//                "Test comment text with more than 5 characters",
//                creationDate,
//                CommentStatus.INFORMATION,
//                taskId
//        );
//
//        // When
//        Comment result = commentMapper.mapToComment(dto);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(dto.getId(), result.getId());
//        assertEquals(dto.getText(), result.getText());
//        assertEquals(dto.getCreation(), result.getCreation());
//        assertEquals(dto.getCommentStatus(), result.getCommentStatus());
//        assertEquals(dto.getTaskId(), result.getTaskId());
//    }
//
//    @Test
//    void mapToComment_ShouldHandleNullDto() {
//        // Given
//        CommentDto dto = null;
//
//        // When & Then
//        assertThrows(NullPointerException.class, () -> commentMapper.mapToComment(dto));
//    }
//
//    @Test
//    void mapToComment_WithNullIdAndTaskId_ShouldMapNullValues() {
//        // Given
//        LocalDate creationDate = LocalDate.now();
//        CommentDto dto = new CommentDto(
//                null,
//                "Valid text with more than 5 chars",
//                creationDate,
//                CommentStatus.FOR_APPROVAL,
//                null
//        );
//
//        // When
//        Comment result = commentMapper.mapToComment(dto);
//
//        // Then
//        assertNotNull(result);
//        assertNull(result.getId());
//        assertEquals(dto.getText(), result.getText());
//        assertEquals(dto.getCreation(), result.getCreation());
//        assertEquals(dto.getCommentStatus(), result.getCommentStatus());
//        assertNull(result.getTaskId());
//    }
//
//    @Test
//    void mapToCommentDto_ShouldMapAllFieldsCorrectly() {
//        // Given
//        UUID id = UUID.randomUUID();
//        UUID taskId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//
//        Comment comment = new Comment(
//                id,
//                "Another test comment with sufficient length",
//                creationDate,
//                CommentStatus.FOR_NOT_APPROVAL,
//                taskId
//        );
//
//        // When
//        CommentDto result = commentMapper.mapToCommentDto(comment);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(comment.getId(), result.getId());
//        assertEquals(comment.getText(), result.getText());
//        assertEquals(comment.getCreation(), result.getCreation());
//        assertEquals(comment.getCommentStatus(), result.getCommentStatus());
//        assertEquals(comment.getTaskId(), result.getTaskId());
//    }
//
//    @Test
//    void mapToCommentDto_ShouldHandleNullComment() {
//        // Given
//        Comment comment = null;
//
//        // When & Then
//        assertThrows(NullPointerException.class, () -> commentMapper.mapToCommentDto(comment));
//    }
//
//    @Test
//    void mapToCommentDto_WithNullIdAndTaskId_ShouldMapNullValues() {
//        // Given
//        LocalDate creationDate = LocalDate.now();
//        Comment comment = new Comment(
//                null,
//                "Valid text for testing purposes",
//                creationDate,
//                CommentStatus.INFORMATION,
//                null
//        );
//
//        // When
//        CommentDto result = commentMapper.mapToCommentDto(comment);
//
//        // Then
//        assertNotNull(result);
//        assertNull(result.getId());
//        assertEquals(comment.getText(), result.getText());
//        assertEquals(comment.getCreation(), result.getCreation());
//        assertEquals(comment.getCommentStatus(), result.getCommentStatus());
//        assertNull(result.getTaskId());
//    }
//
//    @Test
//    void bidirectionalMapping_ShouldBeSymmetric() {
//        // Given
//        UUID id = UUID.randomUUID();
//        UUID taskId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//
//        Comment originalComment = new Comment(
//                id,
//                "Symmetric test comment with enough characters",
//                creationDate,
//                CommentStatus.FOR_APPROVAL,
//                taskId
//        );
//
//        // When
//        CommentDto dto = commentMapper.mapToCommentDto(originalComment);
//        Comment mappedComment = commentMapper.mapToComment(dto);
//
//        // Then
//        assertEquals(originalComment.getId(), mappedComment.getId());
//        assertEquals(originalComment.getText(), mappedComment.getText());
//        assertEquals(originalComment.getCreation(), mappedComment.getCreation());
//        assertEquals(originalComment.getCommentStatus(), mappedComment.getCommentStatus());
//        assertEquals(originalComment.getTaskId(), mappedComment.getTaskId());
//    }
//
//    @Test
//    void mapToComment_WithAllCommentStatusValues_ShouldMapCorrectly() {
//        // Given
//        UUID id = UUID.randomUUID();
//        UUID taskId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//
//        CommentDto dtoInformation = new CommentDto(
//                id,
//                "Information status test comment",
//                creationDate,
//                CommentStatus.INFORMATION,
//                taskId
//        );
//
//        CommentDto dtoForApproval = new CommentDto(
//                id,
//                "For approval status test comment",
//                creationDate,
//                CommentStatus.FOR_APPROVAL,
//                taskId
//        );
//
//        CommentDto dtoForNotApproval = new CommentDto(
//                id,
//                "For not approval status test comment",
//                creationDate,
//                CommentStatus.FOR_NOT_APPROVAL,
//                taskId
//        );
//
//        // When
//        Comment resultInformation = commentMapper.mapToComment(dtoInformation);
//        Comment resultForApproval = commentMapper.mapToComment(dtoForApproval);
//        Comment resultForNotApproval = commentMapper.mapToComment(dtoForNotApproval);
//
//        // Then
//        assertEquals(CommentStatus.INFORMATION, resultInformation.getCommentStatus());
//        assertEquals(CommentStatus.FOR_APPROVAL, resultForApproval.getCommentStatus());
//        assertEquals(CommentStatus.FOR_NOT_APPROVAL, resultForNotApproval.getCommentStatus());
//    }
//
//    @Test
//    void mapToCommentDto_WithAllCommentStatusValues_ShouldMapCorrectly() {
//        // Given
//        UUID id = UUID.randomUUID();
//        UUID taskId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//
//        Comment commentInformation = new Comment(
//                id,
//                "Information status test comment",
//                creationDate,
//                CommentStatus.INFORMATION,
//                taskId
//        );
//
//        Comment commentForApproval = new Comment(
//                id,
//                "For approval status test comment",
//                creationDate,
//                CommentStatus.FOR_APPROVAL,
//                taskId
//        );
//
//        Comment commentForNotApproval = new Comment(
//                id,
//                "For not approval status test comment",
//                creationDate,
//                CommentStatus.FOR_NOT_APPROVAL,
//                taskId
//        );
//
//        // When
//        CommentDto resultInformation = commentMapper.mapToCommentDto(commentInformation);
//        CommentDto resultForApproval = commentMapper.mapToCommentDto(commentForApproval);
//        CommentDto resultForNotApproval = commentMapper.mapToCommentDto(commentForNotApproval);
//
//        // Then
//        assertEquals(CommentStatus.INFORMATION, resultInformation.getCommentStatus());
//        assertEquals(CommentStatus.FOR_APPROVAL, resultForApproval.getCommentStatus());
//        assertEquals(CommentStatus.FOR_NOT_APPROVAL, resultForNotApproval.getCommentStatus());
//    }
//
//    @Test
//    void mapToComment_WithEmptyText_ShouldMapEmptyString() {
//        // Given
//        UUID id = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//
//        CommentDto dto = new CommentDto(
//                id,
//                "", // empty string
//                creationDate,
//                CommentStatus.INFORMATION,
//                null
//        );
//
//        // When
//        Comment result = commentMapper.mapToComment(dto);
//
//        // Then
//        assertNotNull(result);
//        assertEquals("", result.getText());
//    }
//
//    @Test
//    void mapToCommentDto_WithEmptyText_ShouldMapEmptyString() {
//        // Given
//        UUID id = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//
//        Comment comment = new Comment(
//                id,
//                "", // empty string
//                creationDate,
//                CommentStatus.FOR_APPROVAL,
//                null
//        );
//
//        // When
//        CommentDto result = commentMapper.mapToCommentDto(comment);
//
//        // Then
//        assertNotNull(result);
//        assertEquals("", result.getText());
//    }
//
//    @Test
//    void mapToComment_WithNullCreationDate_ShouldMapNullValue() {
//        // Given
//        UUID id = UUID.randomUUID();
//        CommentDto dto = new CommentDto(
//                id,
//                "Test comment with null creation date",
//                null,
//                CommentStatus.FOR_NOT_APPROVAL,
//                null
//        );
//
//        // When
//        Comment result = commentMapper.mapToComment(dto);
//
//        // Then
//        assertNotNull(result);
//        assertNull(result.getCreation());
//    }
//
//    @Test
//    void mapToCommentDto_WithNullCreationDate_ShouldMapNullValue() {
//        // Given
//        UUID id = UUID.randomUUID();
//        Comment comment = new Comment(
//                id,
//                "Test comment with null creation date",
//                null,
//                CommentStatus.INFORMATION,
//                null
//        );
//
//        // When
//        CommentDto result = commentMapper.mapToCommentDto(comment);
//
//        // Then
//        assertNotNull(result);
//        assertNull(result.getCreation());
//    }
}
