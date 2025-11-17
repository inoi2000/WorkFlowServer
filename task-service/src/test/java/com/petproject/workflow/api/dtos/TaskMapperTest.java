package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.*;
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
class TaskMapperTest {

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private TaskMapper taskMapper;

//    @Test
//    void mapToTask_ShouldMapAllFieldsCorrectly() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID executorId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//        LocalDate deadlineDate = creationDate.plusDays(7);
//
//        CommentDto commentDto1 = new CommentDto(UUID.randomUUID(), "Comment 1", LocalDate.now(), CommentStatus.INFORMATION, taskId);
//        CommentDto commentDto2 = new CommentDto(UUID.randomUUID(), "Comment 2", LocalDate.now(), CommentStatus.FOR_APPROVAL, taskId);
//        List<CommentDto> commentDtos = List.of(commentDto1, commentDto2);
//
//        Comment comment1 = new Comment(commentDto1.getId(), commentDto1.getText(), commentDto1.getCreation(), commentDto1.getCommentStatus(), commentDto1.getTaskId());
//        Comment comment2 = new Comment(commentDto2.getId(), commentDto2.getText(), commentDto2.getCreation(), commentDto2.getCommentStatus(), commentDto2.getTaskId());
//        List<Comment> expectedComments = List.of(comment1, comment2);
//
//        EmployeeDto executorDto = new EmployeeDto(executorId, "John Doe");
//        EmployeeDto inspectorDto = new EmployeeDto(inspectorId, "Jane Smith");
//
//        TaskDto dto = new TaskDto(
//                taskId,
//                "Test task description with sufficient length",
//                TaskStatus.NEW,
//                TaskPriority.COMMON,
//                creationDate,
//                deadlineDate,
//                "Test destination",
//                true,
//                executorDto,
//                inspectorDto,
//                commentDtos
//        );
//
//        when(commentMapper.mapToComment(commentDto1)).thenReturn(comment1);
//        when(commentMapper.mapToComment(commentDto2)).thenReturn(comment2);
//
//        // When
//        Task result = taskMapper.mapToTask(dto);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(dto.getId(), result.getId());
//        assertEquals(dto.getDescription(), result.getDescription());
//        assertEquals(dto.getStatus(), result.getStatus());
//        assertEquals(dto.getPriority(), result.getPriority());
//        assertEquals(dto.getCreation(), result.getCreation());
//        assertEquals(dto.getDeadline(), result.getDeadline());
//        assertEquals(dto.getDestination(), result.getDestination());
//        assertEquals(dto.isShouldBeInspected(), result.isShouldBeInspected());
//        assertEquals(executorDto.getId(), result.getExecutor());
//        assertEquals(inspectorDto.getId(), result.getInspector());
//        assertEquals(expectedComments.size(), result.getComments().size());
//
//        verify(commentMapper, times(2)).mapToComment(any(CommentDto.class));
//    }
//
//    @Test
//    void mapToTask_ShouldHandleEmptyCommentsList() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID executorId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//        LocalDate deadlineDate = creationDate.plusDays(7);
//
//        EmployeeDto executorDto = new EmployeeDto(executorId, "John Doe");
//        EmployeeDto inspectorDto = new EmployeeDto(inspectorId, "Jane Smith");
//
//        TaskDto dto = new TaskDto(
//                taskId,
//                "Test task with empty comments",
//                TaskStatus.IN_PROGRESS,
//                TaskPriority.URGENT,
//                creationDate,
//                deadlineDate,
//                "Empty comments destination",
//                false,
//                executorDto,
//                inspectorDto,
//                List.of()
//        );
//
//        // When
//        Task result = taskMapper.mapToTask(dto);
//
//        // Then
//        assertNotNull(result);
//        assertTrue(result.getComments().isEmpty());
//        verify(commentMapper, never()).mapToComment(any(CommentDto.class));
//    }
//
//    @Test
//    void mapToTask_ShouldHandleNullComments() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID executorId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//        LocalDate deadlineDate = creationDate.plusDays(7);
//
//        EmployeeDto executorDto = new EmployeeDto(executorId, "John Doe");
//        EmployeeDto inspectorDto = new EmployeeDto(inspectorId, "Jane Smith");
//
//        TaskDto dto = new TaskDto(
//                taskId,
//                "Test task with null comments",
//                TaskStatus.COMPLETED,
//                TaskPriority.COMMON,
//                creationDate,
//                deadlineDate,
//                "Null comments destination",
//                true,
//                executorDto,
//                inspectorDto,
//                null
//        );
//
//        // When
//        Task result = taskMapper.mapToTask(dto);
//
//        // Then
//        assertNotNull(result);
//        assertNotNull(result.getComments());
//        verify(commentMapper, never()).mapToComment(any(CommentDto.class));
//    }
//
//    @Test
//    void mapToTask_ShouldHandleNullDto() {
//        // Given
//        TaskDto dto = null;
//
//        // When & Then
//        assertThrows(NullPointerException.class, () -> taskMapper.mapToTask(dto));
//    }
//
//    @Test
//    void mapToTaskDto_ShouldMapAllFieldsCorrectly() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID executorId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//        LocalDate deadlineDate = creationDate.plusDays(7);
//
//        Comment comment1 = new Comment(UUID.randomUUID(), "Comment 1", LocalDate.now(), CommentStatus.INFORMATION, taskId);
//        Comment comment2 = new Comment(UUID.randomUUID(), "Comment 2", LocalDate.now(), CommentStatus.FOR_APPROVAL, taskId);
//        List<Comment> comments = List.of(comment1, comment2);
//
//        CommentDto commentDto1 = new CommentDto(comment1.getId(), comment1.getText(), comment1.getCreation(), comment1.getCommentStatus(), comment1.getTaskId());
//        CommentDto commentDto2 = new CommentDto(comment2.getId(), comment2.getText(), comment2.getCreation(), comment2.getCommentStatus(), comment2.getTaskId());
//        List<CommentDto> expectedCommentDtos = List.of(commentDto1, commentDto2);
//
//        EmployeeDto executorDto = new EmployeeDto(executorId, "John Doe");
//        EmployeeDto inspectorDto = new EmployeeDto(inspectorId, "Jane Smith");
//
//        Task task = new Task(
//                taskId,
//                "Test task description with sufficient length",
//                TaskStatus.NEW,
//                TaskPriority.COMMON,
//                creationDate,
//                deadlineDate,
//                "Test destination",
//                true,
//                executorId,
//                inspectorId,
//                comments
//        );
//
//        when(commentMapper.mapToCommentDto(comment1)).thenReturn(commentDto1);
//        when(commentMapper.mapToCommentDto(comment2)).thenReturn(commentDto2);
//
//        // When
//        TaskDto result = taskMapper.mapToTaskDto(task, executorDto, inspectorDto);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(task.getId(), result.getId());
//        assertEquals(task.getDescription(), result.getDescription());
//        assertEquals(task.getStatus(), result.getStatus());
//        assertEquals(task.getPriority(), result.getPriority());
//        assertEquals(task.getCreation(), result.getCreation());
//        assertEquals(task.getDeadline(), result.getDeadline());
//        assertEquals(task.getDestination(), result.getDestination());
//        assertEquals(task.isShouldBeInspected(), result.isShouldBeInspected());
//        assertEquals(executorDto, result.getExecutor());
//        assertEquals(inspectorDto, result.getInspector());
//        assertEquals(expectedCommentDtos.size(), result.getComments().size());
//
//        verify(commentMapper, times(2)).mapToCommentDto(any(Comment.class));
//    }
//
//    @Test
//    void mapToTaskDto_ShouldHandleEmptyCommentsList() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID executorId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//        LocalDate deadlineDate = creationDate.plusDays(7);
//
//        EmployeeDto executorDto = new EmployeeDto(executorId, "John Doe");
//        EmployeeDto inspectorDto = new EmployeeDto(inspectorId, "Jane Smith");
//
//        Task task = new Task(
//                taskId,
//                "Test task with empty comments",
//                TaskStatus.FAILED,
//                TaskPriority.URGENT,
//                creationDate,
//                deadlineDate,
//                "Empty comments destination",
//                false,
//                executorId,
//                inspectorId,
//                List.of()
//        );
//
//        // When
//        TaskDto result = taskMapper.mapToTaskDto(task, executorDto, inspectorDto);
//
//        // Then
//        assertNotNull(result);
//        assertTrue(result.getComments().isEmpty());
//        verify(commentMapper, never()).mapToCommentDto(any(Comment.class));
//    }
//
//    @Test
//    void mapToTaskDto_ShouldHandleNullComments() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID executorId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//        LocalDate deadlineDate = creationDate.plusDays(7);
//
//        EmployeeDto executorDto = new EmployeeDto(executorId, "John Doe");
//        EmployeeDto inspectorDto = new EmployeeDto(inspectorId, "Jane Smith");
//
//        Task task = new Task(
//                taskId,
//                "Test task with null comments",
//                TaskStatus.ON_APPROVAL,
//                TaskPriority.COMMON,
//                creationDate,
//                deadlineDate,
//                "Null comments destination",
//                true,
//                executorId,
//                inspectorId,
//                null
//        );
//
//        // When
//        TaskDto result = taskMapper.mapToTaskDto(task, executorDto, inspectorDto);
//
//        // Then
//        assertNotNull(result);
//        assertNotNull(result.getComments());
//        verify(commentMapper, never()).mapToCommentDto(any(Comment.class));
//    }
//
//    @Test
//    void mapToTaskDto_ShouldHandleNullTask() {
//        // Given
//        Task task = null;
//        EmployeeDto executorDto = new EmployeeDto(UUID.randomUUID(), "John Doe");
//        EmployeeDto inspectorDto = new EmployeeDto(UUID.randomUUID(), "Jane Smith");
//
//        // When & Then
//        assertThrows(NullPointerException.class, () -> taskMapper.mapToTaskDto(task, executorDto, inspectorDto));
//    }
//
//    @Test
//    void mapToTaskDto_ShouldHandleNullEmployeeDtos() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID executorId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//        LocalDate deadlineDate = creationDate.plusDays(7);
//
//        Task task = new Task(
//                taskId,
//                "Test task with null employee DTOs",
//                TaskStatus.NOT_APPROVAL,
//                TaskPriority.URGENT,
//                creationDate,
//                deadlineDate,
//                "Null employee DTOs destination",
//                false,
//                executorId,
//                inspectorId,
//                List.of()
//        );
//
//        // When
//        TaskDto result = taskMapper.mapToTaskDto(task, null, null);
//
//        // Then
//        assertNotNull(result);
//        assertNull(result.getExecutor());
//        assertNull(result.getInspector());
//    }
//
//    @Test
//    void mapToTask_WithAllTaskStatusValues_ShouldMapCorrectly() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID executorId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//        LocalDate deadlineDate = creationDate.plusDays(7);
//
//        EmployeeDto executorDto = new EmployeeDto(executorId, "John Doe");
//        EmployeeDto inspectorDto = new EmployeeDto(inspectorId, "Jane Smith");
//
//        TaskStatus[] statuses = TaskStatus.values();
//
//        for (TaskStatus status : statuses) {
//            TaskDto dto = new TaskDto(
//                    taskId,
//                    "Test task with status: " + status,
//                    status,
//                    TaskPriority.COMMON,
//                    creationDate,
//                    deadlineDate,
//                    "Test destination",
//                    true,
//                    executorDto,
//                    inspectorDto,
//                    List.of()
//            );
//
//            // When
//            Task result = taskMapper.mapToTask(dto);
//
//            // Then
//            assertEquals(status, result.getStatus());
//        }
//    }
//
//    @Test
//    void mapToTask_WithAllTaskPriorityValues_ShouldMapCorrectly() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID executorId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//        LocalDate deadlineDate = creationDate.plusDays(7);
//
//        EmployeeDto executorDto = new EmployeeDto(executorId, "John Doe");
//        EmployeeDto inspectorDto = new EmployeeDto(inspectorId, "Jane Smith");
//
//        TaskPriority[] priorities = TaskPriority.values();
//
//        for (TaskPriority priority : priorities) {
//            TaskDto dto = new TaskDto(
//                    taskId,
//                    "Test task with priority: " + priority,
//                    TaskStatus.NEW,
//                    priority,
//                    creationDate,
//                    deadlineDate,
//                    "Test destination",
//                    true,
//                    executorDto,
//                    inspectorDto,
//                    List.of()
//            );
//
//            // When
//            Task result = taskMapper.mapToTask(dto);
//
//            // Then
//            assertEquals(priority, result.getPriority());
//        }
//    }
//
//    @Test
//    void mapToTask_WithNullDestination_ShouldMapNullValue() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID executorId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//        LocalDate deadlineDate = creationDate.plusDays(7);
//
//        EmployeeDto executorDto = new EmployeeDto(executorId, "John Doe");
//        EmployeeDto inspectorDto = new EmployeeDto(inspectorId, "Jane Smith");
//
//        TaskDto dto = new TaskDto(
//                taskId,
//                "Test task with null destination",
//                TaskStatus.CANCELED,
//                TaskPriority.COMMON,
//                creationDate,
//                deadlineDate,
//                null,
//                false,
//                executorDto,
//                inspectorDto,
//                List.of()
//        );
//
//        // When
//        Task result = taskMapper.mapToTask(dto);
//
//        // Then
//        assertNotNull(result);
//        assertNull(result.getDestination());
//    }
//
//    @Test
//    void mapToTaskDto_WithNullDestination_ShouldMapNullValue() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID executorId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//        LocalDate deadlineDate = creationDate.plusDays(7);
//
//        EmployeeDto executorDto = new EmployeeDto(executorId, "John Doe");
//        EmployeeDto inspectorDto = new EmployeeDto(inspectorId, "Jane Smith");
//
//        Task task = new Task(
//                taskId,
//                "Test task with null destination",
//                TaskStatus.CANCELED,
//                TaskPriority.COMMON,
//                creationDate,
//                deadlineDate,
//                null,
//                false,
//                executorId,
//                inspectorId,
//                List.of()
//        );
//
//        // When
//        TaskDto result = taskMapper.mapToTaskDto(task, executorDto, inspectorDto);
//
//        // Then
//        assertNotNull(result);
//        assertNull(result.getDestination());
//    }
//
//    @Test
//    void bidirectionalMapping_ShouldBeSymmetricForBasicFields() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID executorId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//        LocalDate deadlineDate = creationDate.plusDays(7);
//
//        EmployeeDto executorDto = new EmployeeDto(executorId, "John Doe");
//        EmployeeDto inspectorDto = new EmployeeDto(inspectorId, "Jane Smith");
//
//        TaskDto originalDto = new TaskDto(
//                taskId,
//                "Symmetric test task description",
//                TaskStatus.IN_PROGRESS,
//                TaskPriority.URGENT,
//                creationDate,
//                deadlineDate,
//                "Symmetric destination",
//                true,
//                executorDto,
//                inspectorDto,
//                List.of()
//        );
//
//        // When
//        Task task = taskMapper.mapToTask(originalDto);
//        TaskDto resultDto = taskMapper.mapToTaskDto(task, executorDto, inspectorDto);
//
//        // Then
//        assertEquals(originalDto.getId(), resultDto.getId());
//        assertEquals(originalDto.getDescription(), resultDto.getDescription());
//        assertEquals(originalDto.getStatus(), resultDto.getStatus());
//        assertEquals(originalDto.getPriority(), resultDto.getPriority());
//        assertEquals(originalDto.getCreation(), resultDto.getCreation());
//        assertEquals(originalDto.getDeadline(), resultDto.getDeadline());
//        assertEquals(originalDto.getDestination(), resultDto.getDestination());
//        assertEquals(originalDto.isShouldBeInspected(), resultDto.isShouldBeInspected());
//        assertEquals(originalDto.getExecutor(), resultDto.getExecutor());
//        assertEquals(originalDto.getInspector(), resultDto.getInspector());
//    }
//
//    @Test
//    void mapToTask_WithEmployeeDtoHavingOnlyId_ShouldMapOnlyIdToTask() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID executorId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//        LocalDate creationDate = LocalDate.now();
//        LocalDate deadlineDate = creationDate.plusDays(7);
//
//        EmployeeDto executorDto = new EmployeeDto(executorId, null); // Only ID
//        EmployeeDto inspectorDto = new EmployeeDto(inspectorId, "Jane Smith");
//
//        TaskDto dto = new TaskDto(
//                taskId,
//                "Test task with employee having only ID",
//                TaskStatus.NEW,
//                TaskPriority.COMMON,
//                creationDate,
//                deadlineDate,
//                "Test destination",
//                true,
//                executorDto,
//                inspectorDto,
//                List.of()
//        );
//
//        // When
//        Task result = taskMapper.mapToTask(dto);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(executorId, result.getExecutor());
//        assertEquals(inspectorId, result.getInspector());
//    }
}