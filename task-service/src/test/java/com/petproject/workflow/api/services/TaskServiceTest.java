package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.EmployeeDto;
import com.petproject.workflow.api.dtos.TaskDto;
import com.petproject.workflow.api.dtos.TaskMapper;
import com.petproject.workflow.store.Task;
import com.petproject.workflow.store.TaskPriority;
import com.petproject.workflow.store.TaskRepository;
import com.petproject.workflow.store.TaskStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private EmployeeServiceClient employeeServiceClient;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

//    @Test
//    void createTask_ShouldCreateTaskWithGeneratedValues() {
//        // Given
//        UUID executorId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//        LocalDate deadline = LocalDate.now().plusDays(7);
//
//        TaskDto inputDto = new TaskDto(
//                null,
//                "Test task description",
//                null,
//                TaskPriority.COMMON,
//                null,
//                deadline,
//                "Test destination",
//                true,
//                new EmployeeDto(executorId, "Executor"),
//                new EmployeeDto(inspectorId, "Inspector"),
//                List.of()
//        );
//
//        UUID generatedId = UUID.randomUUID();
//        Task taskToSave = new Task(
//                generatedId,
//                inputDto.getDescription(),
//                TaskStatus.NEW,
//                inputDto.getPriority(),
//                LocalDate.now(),
//                inputDto.getDeadline(),
//                inputDto.getDestination(),
//                inputDto.isShouldBeInspected(),
//                executorId,
//                inspectorId,
//                List.of()
//        );
//
//        Task savedTask = new Task(
//                generatedId,
//                inputDto.getDescription(),
//                TaskStatus.NEW,
//                inputDto.getPriority(),
//                LocalDate.now(),
//                inputDto.getDeadline(),
//                inputDto.getDestination(),
//                inputDto.isShouldBeInspected(),
//                executorId,
//                inspectorId,
//                List.of()
//        );
//
//        EmployeeDto executorDto = new EmployeeDto(executorId, "Executor");
//        EmployeeDto inspectorDto = new EmployeeDto(inspectorId, "Inspector");
//        TaskDto expectedDto = new TaskDto(
//                generatedId,
//                inputDto.getDescription(),
//                TaskStatus.NEW,
//                inputDto.getPriority(),
//                LocalDate.now(),
//                inputDto.getDeadline(),
//                inputDto.getDestination(),
//                inputDto.isShouldBeInspected(),
//                executorDto,
//                inspectorDto,
//                List.of()
//        );
//
//        when(taskMapper.mapToTask(inputDto)).thenReturn(taskToSave);
//        when(taskRepository.save(taskToSave)).thenReturn(savedTask);
//        when(employeeServiceClient.getEmployeesByIds(anySet())).thenReturn(List.of(
//                executorDto,
//                inspectorDto
//        ));
//
//        // Используем eq() для сравнения по значению, а не по ссылке
//        when(taskMapper.mapToTaskDto(eq(savedTask), eq(executorDto), eq(inspectorDto)))
//                .thenReturn(expectedDto);
//
//        // When
//        TaskDto result = taskService.createTask(inputDto);
//
//        // Then
//        assertNotNull(result);
//        assertNotNull(result.getId());
//        assertEquals(TaskStatus.NEW, result.getStatus());
//        assertNotNull(result.getCreation());
//        assertEquals(inputDto.getDescription(), result.getDescription());
//
//        verify(taskMapper).mapToTask(inputDto);
//        verify(taskRepository).save(taskToSave);
//    }
//
//    @Test
//    void getTaskById_ShouldReturnTask_WhenExists() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID executorId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//
//        Task task = new Task(
//                taskId,
//                "Test task",
//                TaskStatus.NEW,
//                TaskPriority.COMMON,
//                LocalDate.now(),
//                LocalDate.now().plusDays(7),
//                "Destination",
//                true,
//                executorId,
//                inspectorId,
//                List.of()
//        );
//
//        EmployeeDto executorDto = new EmployeeDto(executorId, "Executor");
//        EmployeeDto inspectorDto = new EmployeeDto(inspectorId, "Inspector");
//        TaskDto expectedDto = new TaskDto(
//                taskId,
//                "Test task",
//                TaskStatus.NEW,
//                TaskPriority.COMMON,
//                LocalDate.now(),
//                LocalDate.now().plusDays(7),
//                "Destination",
//                true,
//                executorDto,
//                inspectorDto,
//                List.of()
//        );
//
//        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
//        when(employeeServiceClient.getEmployeesByIds(anySet())).thenReturn(List.of(
//                executorDto,
//                inspectorDto
//        ));
//        when(taskMapper.mapToTaskDto(eq(task), eq(executorDto), eq(inspectorDto)))
//                .thenReturn(expectedDto);
//
//        // When
//        Optional<TaskDto> result = taskService.getTaskById(taskId);
//
//        // Then
//        assertTrue(result.isPresent());
//        assertEquals(expectedDto, result.get());
//        verify(taskRepository).findById(taskId);
//    }
//
//    @Test
//    void getAllTasksByExecutor_ShouldReturnTasks() {
//        // Given
//        UUID executorId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//
//        Task task1 = new Task(
//                UUID.randomUUID(),
//                "Task 1",
//                TaskStatus.NEW,
//                TaskPriority.COMMON,
//                LocalDate.now(),
//                LocalDate.now().plusDays(7),
//                "Dest 1",
//                true,
//                executorId,
//                inspectorId,
//                List.of()
//        );
//
//        Task task2 = new Task(
//                UUID.randomUUID(),
//                "Task 2",
//                TaskStatus.IN_PROGRESS,
//                TaskPriority.URGENT,
//                LocalDate.now(),
//                LocalDate.now().plusDays(5),
//                "Dest 2",
//                false,
//                executorId,
//                inspectorId,
//                List.of()
//        );
//
//        List<Task> tasks = List.of(task1, task2);
//
//        EmployeeDto executorDto = new EmployeeDto(executorId, "Executor");
//        EmployeeDto inspectorDto = new EmployeeDto(inspectorId, "Inspector");
//
//        TaskDto dto1 = new TaskDto(task1.getId(), "Task 1", TaskStatus.NEW, TaskPriority.COMMON,
//                task1.getCreation(), task1.getDeadline(), "Dest 1", true,
//                executorDto, inspectorDto, List.of());
//
//        TaskDto dto2 = new TaskDto(task2.getId(), "Task 2", TaskStatus.IN_PROGRESS, TaskPriority.URGENT,
//                task2.getCreation(), task2.getDeadline(), "Dest 2", false,
//                executorDto, inspectorDto, List.of());
//
//        when(taskRepository.findAllByExecutor(executorId)).thenReturn(tasks);
//        when(employeeServiceClient.getEmployeesByIds(anySet())).thenReturn(List.of(
//                executorDto,
//                inspectorDto
//        ));
//        when(taskMapper.mapToTaskDto(eq(task1), eq(executorDto), eq(inspectorDto)))
//                .thenReturn(dto1);
//        when(taskMapper.mapToTaskDto(eq(task2), eq(executorDto), eq(inspectorDto)))
//                .thenReturn(dto2);
//
//        // When
//        Iterable<TaskDto> result = taskService.getAllTasksByExecutor(executorId);
//
//        // Then
//        assertNotNull(result);
//        List<TaskDto> resultList = (List<TaskDto>) result;
//        assertEquals(2, resultList.size());
//        verify(taskRepository).findAllByExecutor(executorId);
//    }
//
//    // Остальные тесты аналогично с использованием eq()...
//
//    @Test
//    void acceptTask_ShouldAcceptTask_WhenValid() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//
//        Task task = new Task(
//                taskId,
//                "Test task",
//                TaskStatus.NEW,
//                TaskPriority.COMMON,
//                LocalDate.now(),
//                LocalDate.now().plusDays(7),
//                "Destination",
//                true,
//                userId,
//                inspectorId,
//                List.of()
//        );
//
//        Task savedTask = new Task(
//                taskId,
//                "Test task",
//                TaskStatus.IN_PROGRESS,
//                TaskPriority.COMMON,
//                LocalDate.now(),
//                LocalDate.now().plusDays(7),
//                "Destination",
//                true,
//                userId,
//                inspectorId,
//                List.of()
//        );
//
//        EmployeeDto executorDto = new EmployeeDto(userId, "Executor");
//        EmployeeDto inspectorDto = new EmployeeDto(inspectorId, "Inspector");
//        TaskDto expectedDto = new TaskDto(
//                taskId,
//                "Test task",
//                TaskStatus.IN_PROGRESS,
//                TaskPriority.COMMON,
//                LocalDate.now(),
//                LocalDate.now().plusDays(7),
//                "Destination",
//                true,
//                executorDto,
//                inspectorDto,
//                List.of()
//        );
//
//        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
//        when(taskRepository.save(savedTask)).thenReturn(savedTask);
//        when(employeeServiceClient.getEmployeesByIds(anySet())).thenReturn(List.of(
//                executorDto,
//                inspectorDto
//        ));
//        when(taskMapper.mapToTaskDto(eq(savedTask), eq(executorDto), eq(inspectorDto)))
//                .thenReturn(expectedDto);
//
//        // When
//        Optional<TaskDto> result = taskService.acceptTask(taskId, userId);
//
//        // Then
//        assertTrue(result.isPresent());
//        assertEquals(TaskStatus.IN_PROGRESS, result.get().getStatus());
//        verify(taskRepository).findById(taskId);
//        verify(taskRepository).save(savedTask);
//    }
//
//    // Для методов, где точные значения не важны, можно использовать any()
//    @Test
//    void submitTask_ShouldSubmitForApproval_WhenShouldBeInspected() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//        UUID inspectorId = UUID.randomUUID();
//
//        Task task = new Task(
//                taskId,
//                "Test task",
//                TaskStatus.IN_PROGRESS,
//                TaskPriority.COMMON,
//                LocalDate.now(),
//                LocalDate.now().plusDays(7),
//                "Destination",
//                true,
//                userId,
//                inspectorId,
//                List.of()
//        );
//
//        Task savedTask = new Task(
//                taskId,
//                "Test task",
//                TaskStatus.ON_APPROVAL,
//                TaskPriority.COMMON,
//                LocalDate.now(),
//                LocalDate.now().plusDays(7),
//                "Destination",
//                true,
//                userId,
//                inspectorId,
//                List.of()
//        );
//
//        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
//        when(taskRepository.save(savedTask)).thenReturn(savedTask);
//        when(employeeServiceClient.getEmployeesByIds(anySet())).thenReturn(List.of(
//                new EmployeeDto(userId, "Executor"),
//                new EmployeeDto(inspectorId, "Inspector")
//        ));
//
//        // Используем any() если точные значения не важны для теста
//        when(taskMapper.mapToTaskDto(any(Task.class), any(EmployeeDto.class), any(EmployeeDto.class)))
//                .thenReturn(new TaskDto());
//
//        // When
//        Optional<TaskDto> result = taskService.submitTask(taskId, userId);
//
//        // Then
//        assertTrue(result.isPresent());
//        verify(taskRepository).save(savedTask);
//    }
//
//    // Альтернативный подход - использовать lenient() для избежания конфликтов
//    @Test
//    void approvalTask_ShouldApproveTask() {
//        // Given
//        UUID taskId = UUID.randomUUID();
//        UUID userId = UUID.randomUUID();
//        UUID executorId = UUID.randomUUID();
//
//        Task task = new Task(
//                taskId,
//                "Test task",
//                TaskStatus.ON_APPROVAL,
//                TaskPriority.COMMON,
//                LocalDate.now(),
//                LocalDate.now().plusDays(7),
//                "Destination",
//                true,
//                executorId,
//                userId,
//                List.of()
//        );
//
//        Task savedTask = new Task(
//                taskId,
//                "Test task",
//                TaskStatus.COMPLETED,
//                TaskPriority.COMMON,
//                LocalDate.now(),
//                LocalDate.now().plusDays(7),
//                "Destination",
//                true,
//                executorId,
//                userId,
//                List.of()
//        );
//
//        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
//        when(taskRepository.save(savedTask)).thenReturn(savedTask);
//        when(employeeServiceClient.getEmployeesByIds(anySet())).thenReturn(List.of(
//                new EmployeeDto(executorId, "Executor"),
//                new EmployeeDto(userId, "Inspector")
//        ));
//
//        // Lenient stubbing чтобы избежать конфликтов
//        lenient().when(taskMapper.mapToTaskDto(any(Task.class), any(EmployeeDto.class), any(EmployeeDto.class)))
//                .thenReturn(new TaskDto());
//
//        // When
//        Optional<TaskDto> result = taskService.approvalTask(taskId, userId);
//
//        // Then
//        assertTrue(result.isPresent());
//        verify(taskRepository).save(savedTask);
//    }
}