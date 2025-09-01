package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.EmployeeDto;
import com.petproject.workflow.api.dtos.TaskDto;
import com.petproject.workflow.store.Task;
import com.petproject.workflow.store.TaskRepository;
import com.petproject.workflow.store.TaskStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeServiceClient employeeServiceClient;

    public TaskService(TaskRepository taskRepository, EmployeeServiceClient employeeServiceClient) {
        this.taskRepository = taskRepository;
        this.employeeServiceClient = employeeServiceClient;
    }

    public TaskDto createTask(Task task) {
        task.setId(UUID.randomUUID());
        task.setStatus(TaskStatus.NEW);
        return toTaskDto(taskRepository.save(task));
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
        Iterable<Task> tasks = taskRepository.findAllByExecutor(executorId);
        Set<UUID> employeeIds = collectEmployeesUUIDtoSet(tasks);
        Map<UUID, EmployeeDto> employees = toMap(employeeServiceClient.getEmployeesByIds(employeeIds));
        return StreamSupport.stream(tasks.spliterator(), false).map(task ->
                new TaskDto(
                        task,
                        employees.get(task.getExecutor()),
                        employees.get(task.getInspector())
                )
        ).toList();
    }

    public Iterable<TaskDto> getAllTasksByInspector(UUID inspectorId) {
        Iterable<Task> tasks = taskRepository.findAllByInspector(inspectorId);
        Set<UUID> employeeIds = collectEmployeesUUIDtoSet(tasks);
        Map<UUID, EmployeeDto> employees = toMap(employeeServiceClient.getEmployeesByIds(employeeIds));
        return StreamSupport.stream(tasks.spliterator(), false).map(task ->
                new TaskDto(
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
                    return toTaskDto(taskRepository.save(task));
                });
    }

    public Optional<TaskDto> submitTask(UUID taskId, UUID userId) throws IllegalStateException {
        return taskRepository.findById(taskId)
                .filter(task -> task.getExecutor().equals(userId))
                .map(task -> {
                    if (task.getStatus() != TaskStatus.IN_PROGRESS && task.getStatus() != TaskStatus.NOT_APPROVAL) {
                        throw new IllegalStateException();
                    }
                    if (task.isShouldBeInspected()) {
                        task.setStatus(TaskStatus.ON_APPROVAL);
                    } else {
                        task.setStatus(TaskStatus.COMPLETED);
                    }
                    return toTaskDto(taskRepository.save(task));
                });
    }

    public Optional<TaskDto> approvalTask(UUID taskId, UUID userId) {
        return taskRepository.findById(taskId)
                .filter(task -> task.getInspector().equals(userId))
                .map(task -> {
                    task.setStatus(TaskStatus.COMPLETED);
                    return toTaskDto(taskRepository.save(task));
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
                    return toTaskDto(taskRepository.save(task));
                });
    }

    public Optional<TaskDto> cancelTask(UUID taskId, UUID userId) {
        return taskRepository.findById(taskId)
                .filter(task -> task.getInspector().equals(userId))
                .map(task -> {
                    task.setStatus(TaskStatus.CANCELED);
                    return toTaskDto(taskRepository.save(task));
                });
    }

    private TaskDto toTaskDto(Task task) {
        Set<UUID> employeeIds = collectEmployeeUUIDtoSet(task);
        Map<UUID, EmployeeDto> employees = toMap(employeeServiceClient.getEmployeesByIds(employeeIds));
        return new TaskDto(
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
