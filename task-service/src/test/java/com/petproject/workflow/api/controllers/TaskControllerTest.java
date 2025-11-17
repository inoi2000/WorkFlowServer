package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.EmployeeDto;
import com.petproject.workflow.api.dtos.TaskDto;
import com.petproject.workflow.api.services.TaskService;
import com.petproject.workflow.store.TaskPriority;
import com.petproject.workflow.store.TaskStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private JwtAuthenticationToken authentication;

    @Mock
    private Jwt jwt;

    @InjectMocks
    private TaskController taskController;

//    @Test
//    void getTaskById_ShouldReturnTask_WhenExists() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        TaskDto taskDto = createTestTaskDto(taskId);
//
//        when(taskService.getTaskById(taskId)).thenReturn(Optional.of(taskDto));
//
//        // When
//        ResponseEntity<TaskDto> response = taskController.getTaskById(taskId);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(taskId, response.getBody().getId());
//        verify(taskService).getTaskById(taskId);
//    }
//
//    @Test
//    void getTaskById_ShouldReturnNotFound_WhenNotExists() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        when(taskService.getTaskById(taskId)).thenReturn(Optional.empty());
//
//        // When
//        ResponseEntity<TaskDto> response = taskController.getTaskById(taskId);
//
//        // Then
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertNull(response.getBody());
//        verify(taskService).getTaskById(taskId);
//    }
//
//    @Test
//    void getAllTasksByExecutor_ShouldReturnTasks() {
//        // Given
//        UUID executorId = UUID.randomUUID();
//        List<TaskDto> tasks = List.of(
//                createTestTaskDto(UUID.randomUUID()),
//                createTestTaskDto(UUID.randomUUID())
//        );
//
//        when(taskService.getAllTasksByExecutor(executorId)).thenReturn(tasks);
//
//        // When
//        Iterable<TaskDto> result = taskController.getAllTasksByExecutor(executorId);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(2, ((List<TaskDto>) result).size());
//        verify(taskService).getAllTasksByExecutor(executorId);
//    }
//
//    @Test
//    void getAllTasksByInspector_ShouldReturnTasks() {
//        // Given
//        UUID inspectorId = UUID.randomUUID();
//        List<TaskDto> tasks = List.of(createTestTaskDto(UUID.randomUUID()));
//
//        when(taskService.getAllTasksByInspector(inspectorId)).thenReturn(tasks);
//
//        // When
//        Iterable<TaskDto> result = taskController.getAllTasksByInspector(inspectorId);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(1, ((List<TaskDto>) result).size());
//        verify(taskService).getAllTasksByInspector(inspectorId);
//    }
//
//    @Test
//    void createTask_ShouldCreateTask() {
//        // Given
//        TaskDto inputDto = createTestTaskDto(null);
//        TaskDto createdDto = createTestTaskDto(UUID.randomUUID());
//
//        when(taskService.createTask(inputDto)).thenReturn(createdDto);
//
//        // When
//        TaskDto result = taskController.createTask(inputDto);
//
//        // Then
//        assertNotNull(result);
//        assertNotNull(result.getId());
//        verify(taskService).createTask(inputDto);
//    }
//
//    @Test
//    void acceptTask_ShouldAcceptTask_WhenValid() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//        TaskDto taskDto = createTestTaskDto(taskId);
//
//        when(authentication.getToken()).thenReturn(jwt);
//        when(jwt.getClaimAsString("id")).thenReturn(userId.toString());
//        when(taskService.acceptTask(taskId, userId)).thenReturn(Optional.of(taskDto));
//
//        // When
//        ResponseEntity<TaskDto> response = taskController.acceptTask(taskId, authentication);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(taskId, response.getBody().getId());
//        verify(taskService).acceptTask(taskId, userId);
//    }
//
//    @Test
//    void acceptTask_ShouldReturnNotFound_WhenTaskNotExists() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//
//        when(authentication.getToken()).thenReturn(jwt);
//        when(jwt.getClaimAsString("id")).thenReturn(userId.toString());
//        when(taskService.acceptTask(taskId, userId)).thenReturn(Optional.empty());
//
//        // When
//        ResponseEntity<TaskDto> response = taskController.acceptTask(taskId, authentication);
//
//        // Then
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertNull(response.getBody());
//        verify(taskService).acceptTask(taskId, userId);
//    }
//
//    @Test
//    void acceptTask_ShouldReturnBadRequest_WhenIllegalState() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//
//        when(authentication.getToken()).thenReturn(jwt);
//        when(jwt.getClaimAsString("id")).thenReturn(userId.toString());
//        when(taskService.acceptTask(taskId, userId)).thenThrow(new IllegalStateException());
//
//        // When
//        ResponseEntity<TaskDto> response = taskController.acceptTask(taskId, authentication);
//
//        // Then
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertNull(response.getBody());
//        verify(taskService).acceptTask(taskId, userId);
//    }
//
//    @Test
//    void submitTask_ShouldSubmitTask_WhenValid() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//        TaskDto taskDto = createTestTaskDto(taskId);
//
//        when(authentication.getToken()).thenReturn(jwt);
//        when(jwt.getClaimAsString("id")).thenReturn(userId.toString());
//        when(taskService.submitTask(taskId, userId)).thenReturn(Optional.of(taskDto));
//
//        // When
//        ResponseEntity<TaskDto> response = taskController.submitTask(taskId, authentication);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        verify(taskService).submitTask(taskId, userId);
//    }
//
//    @Test
//    void submitTask_ShouldReturnBadRequest_WhenIllegalState() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//
//        when(authentication.getToken()).thenReturn(jwt);
//        when(jwt.getClaimAsString("id")).thenReturn(userId.toString());
//        when(taskService.submitTask(taskId, userId)).thenThrow(new IllegalStateException());
//
//        // When
//        ResponseEntity<TaskDto> response = taskController.submitTask(taskId, authentication);
//
//        // Then
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertNull(response.getBody());
//        verify(taskService).submitTask(taskId, userId);
//    }
//
//    @Test
//    void approvalTask_ShouldApproveTask_WhenValid() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//        TaskDto taskDto = createTestTaskDto(taskId);
//
//        when(authentication.getToken()).thenReturn(jwt);
//        when(jwt.getClaimAsString("id")).thenReturn(userId.toString());
//        when(taskService.approvalTask(taskId, userId)).thenReturn(Optional.of(taskDto));
//
//        // When
//        ResponseEntity<TaskDto> response = taskController.approvalTask(taskId, authentication);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        verify(taskService).approvalTask(taskId, userId);
//    }
//
//    @Test
//    void approvalTask_ShouldReturnBadRequest_WhenIllegalState() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//
//        when(authentication.getToken()).thenReturn(jwt);
//        when(jwt.getClaimAsString("id")).thenReturn(userId.toString());
//        when(taskService.approvalTask(taskId, userId)).thenThrow(new IllegalStateException());
//
//        // When
//        ResponseEntity<TaskDto> response = taskController.approvalTask(taskId, authentication);
//
//        // Then
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertNull(response.getBody());
//        verify(taskService).approvalTask(taskId, userId);
//    }
//
//    @Test
//    void rejectTask_ShouldRejectTask_WhenValid() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//        TaskDto taskDto = createTestTaskDto(taskId);
//
//        when(authentication.getToken()).thenReturn(jwt);
//        when(jwt.getClaimAsString("id")).thenReturn(userId.toString());
//        when(taskService.rejectTask(taskId, userId)).thenReturn(Optional.of(taskDto));
//
//        // When
//        ResponseEntity<TaskDto> response = taskController.rejectTask(taskId, authentication);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        verify(taskService).rejectTask(taskId, userId);
//    }
//
//    @Test
//    void rejectTask_ShouldReturnBadRequest_WhenIllegalState() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//
//        when(authentication.getToken()).thenReturn(jwt);
//        when(jwt.getClaimAsString("id")).thenReturn(userId.toString());
//        when(taskService.rejectTask(taskId, userId)).thenThrow(new IllegalStateException());
//
//        // When
//        ResponseEntity<TaskDto> response = taskController.rejectTask(taskId, authentication);
//
//        // Then
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertNull(response.getBody());
//        verify(taskService).rejectTask(taskId, userId);
//    }
//
//    @Test
//    void cancelTask_ShouldCancelTask_WhenValid() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//        TaskDto taskDto = createTestTaskDto(taskId);
//
//        when(authentication.getToken()).thenReturn(jwt);
//        when(jwt.getClaimAsString("id")).thenReturn(userId.toString());
//        when(taskService.cancelTask(taskId, userId)).thenReturn(Optional.of(taskDto));
//
//        // When
//        ResponseEntity<TaskDto> response = taskController.cancelTask(taskId, authentication);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        verify(taskService).cancelTask(taskId, userId);
//    }
//
//    @Test
//    void cancelTask_ShouldReturnBadRequest_WhenIllegalState() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//
//        when(authentication.getToken()).thenReturn(jwt);
//        when(jwt.getClaimAsString("id")).thenReturn(userId.toString());
//        when(taskService.cancelTask(taskId, userId)).thenThrow(new IllegalStateException());
//
//        // When
//        ResponseEntity<TaskDto> response = taskController.cancelTask(taskId, authentication);
//
//        // Then
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertNull(response.getBody());
//        verify(taskService).cancelTask(taskId, userId);
//    }
//
//    private TaskDto createTestTaskDto(UUID id) {
//        UUID executorId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//
//        return new TaskDto(
//                id,
//                "Test Task Description",
//                TaskStatus.NEW,
//                TaskPriority.COMMON,
//                LocalDate.now(),
//                LocalDate.now().plusDays(7),
//                "Test Destination",
//                true,
//                new EmployeeDto(executorId, "Executor Name"),
//                new EmployeeDto(inspectorId, "Inspector Name"),
//                List.of()
//        );
//    }
}