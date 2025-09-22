package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.EmployeeDto;
import com.petproject.workflow.api.dtos.TaskDto;
import com.petproject.workflow.api.dtos.TaskMapper;
import com.petproject.workflow.api.dtos.TaskNotificationDto;
import com.petproject.workflow.store.Task;
import com.petproject.workflow.store.TaskRepository;
import com.petproject.workflow.store.TaskStatus;
import jakarta.transaction.Transactional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeServiceClient employeeServiceClient;
    private final KafkaTemplate<String, TaskNotificationDto> kafkaTemplate;

    private final TaskMapper taskMapper;

    public TaskService(
            TaskRepository taskRepository,
            EmployeeServiceClient employeeServiceClient,
            KafkaTemplate<String, TaskNotificationDto> kafkaTemplate,
            TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.employeeServiceClient = employeeServiceClient;
        this.kafkaTemplate = kafkaTemplate;
        this.taskMapper = taskMapper;
    }

    public TaskDto createTask(TaskDto dto) {
        Task task = taskMapper.mapToTask(dto);
        task.setId(UUID.randomUUID());
        task.setStatus(TaskStatus.NEW);
        task.setCreation(LocalDate.now());
        var result = toTaskDto(taskRepository.save(task));
        var notification = new TaskNotificationDto(
                task.getId(),
                task.getExecutor(),
                "Новая задача");
        kafkaTemplate.sendDefault(notification);
        return result;
    }

    public Optional<TaskDto> getTaskById(UUID id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            TaskDto taskDto = toTaskDto(task);
            return Optional.of(taskDto);
        }
        return Optional.empty();
    }

    public Iterable<TaskDto> getAllTasksByExecutor(UUID executorId) {
        List<Task> tasks = taskRepository.findAllByExecutor(executorId);
        Set<UUID> employeeIds = collectEmployeesUUIDtoSet(tasks);
        Map<UUID, EmployeeDto> employees = toMap(employeeServiceClient.getEmployeesByIds(employeeIds));
        return tasks.stream().map(task ->
                taskMapper.mapToTaskDto(
                        task,
                        employees.get(task.getExecutor()),
                        employees.get(task.getInspector())
                )
        ).toList();
    }

    public Iterable<TaskDto> getAllTasksByInspector(UUID inspectorId) {
        List<Task> tasks = taskRepository.findAllByInspector(inspectorId);
        Set<UUID> employeeIds = collectEmployeesUUIDtoSet(tasks);
        Map<UUID, EmployeeDto> employees = toMap(employeeServiceClient.getEmployeesByIds(employeeIds));
        return tasks.stream().map(task ->
                taskMapper.mapToTaskDto(
                        task,
                        employees.get(task.getExecutor()),
                        employees.get(task.getInspector())
                )
        ).toList();
    }

    public Optional<TaskDto> acceptTask(UUID taskId, UUID userId) throws IllegalStateException {
        return taskRepository.findById(taskId)
                .filter(task -> task.getExecutor().equals(userId))
                .map(task -> {
                    if (task.getStatus() != TaskStatus.NEW) {
                        throw new IllegalStateException();
                    }
                    task.setStatus(TaskStatus.IN_PROGRESS);
                    var result = toTaskDto(taskRepository.save(task));
                    var notification = new TaskNotificationDto(
                            task.getId(),
                            task.getInspector(),
                            "Задача принята в работу");
                    kafkaTemplate.sendDefault(notification);
                    return result;
                });
    }

    public Optional<TaskDto> submitTask(UUID taskId, UUID userId) throws IllegalStateException {
        return taskRepository.findById(taskId)
                .filter(task -> task.getExecutor().equals(userId))
                .map(task -> {
                    if (task.getStatus() != TaskStatus.IN_PROGRESS && task.getStatus() != TaskStatus.NOT_APPROVAL) {
                        throw new IllegalStateException();
                    }
                    String text;
                    if (task.isShouldBeInspected()) {
                        task.setStatus(TaskStatus.ON_APPROVAL);
                        text = "Задача находится на утверждении";
                    } else {
                        task.setStatus(TaskStatus.COMPLETED);
                        text = "Порученная задача выполнена";
                    }
                    var result = toTaskDto(taskRepository.save(task));
                    var notification = new TaskNotificationDto(
                            task.getId(),
                            task.getInspector(),
                            text);
                    kafkaTemplate.sendDefault(notification);
                    return result;
                });
    }

    public Optional<TaskDto> approvalTask(UUID taskId, UUID userId) {
        return taskRepository.findById(taskId)
                .filter(task -> task.getInspector().equals(userId))
                .map(task -> {
                    task.setStatus(TaskStatus.COMPLETED);
                    var result = toTaskDto(taskRepository.save(task));
                    var notification = new TaskNotificationDto(
                            task.getId(),
                            task.getExecutor(),
                            "Задача выполнена");
                    kafkaTemplate.sendDefault(notification);
                    return result;
                });
    }

    public Optional<TaskDto> rejectTask(UUID taskId, UUID userId) throws IllegalStateException {
        return taskRepository.findById(taskId)
                .filter(task -> task.getInspector().equals(userId))
                .map(task -> {
                    if (task.getStatus() != TaskStatus.ON_APPROVAL) {
                        throw new IllegalStateException();
                    }
                    task.setStatus(TaskStatus.NOT_APPROVAL);
                    var result = toTaskDto(taskRepository.save(task));
                    var notification = new TaskNotificationDto(
                            task.getId(),
                            task.getExecutor(),
                            "Задача не утверждена");
                    kafkaTemplate.sendDefault(notification);
                    return result;
                });
    }

    public Optional<TaskDto> cancelTask(UUID taskId, UUID userId) {
        return taskRepository.findById(taskId)
                .filter(task -> task.getInspector().equals(userId))
                .map(task -> {
                    task.setStatus(TaskStatus.CANCELED);
                    var result = toTaskDto(taskRepository.save(task));
                    var notification = new TaskNotificationDto(
                            task.getId(),
                            task.getExecutor(),
                            "Задача отменена");
                    kafkaTemplate.sendDefault(notification);
                    return result;
                });
    }

    private TaskDto toTaskDto(Task task) {
        Set<UUID> employeeIds = collectEmployeeUUIDtoSet(task);
        Map<UUID, EmployeeDto> employees = toMap(employeeServiceClient.getEmployeesByIds(employeeIds));
        return taskMapper.mapToTaskDto(
                task,
                employees.get(task.getExecutor()),
                employees.get(task.getInspector())
        );
    }

    private Set<UUID> collectEmployeeUUIDtoSet(Task task) {
        Set<UUID> set = new HashSet<>();
        set.add(task.getExecutor());
        set.add(task.getInspector());
        return set;
    }

    private Set<UUID> collectEmployeesUUIDtoSet(Iterable<Task> tasks) {
        Set<UUID> set = new HashSet<>();
        for (Task task : tasks) {
            set.add(task.getExecutor());
            set.add(task.getInspector());
        }
        return set;
    }

    private Map<UUID, EmployeeDto> toMap(Iterable<EmployeeDto> employees) {
        Map<UUID, EmployeeDto> map = new HashMap<>();
        for (EmployeeDto employee : employees) {
            map.put(employee.getId(), employee);
        }
        return map;
    }
}
