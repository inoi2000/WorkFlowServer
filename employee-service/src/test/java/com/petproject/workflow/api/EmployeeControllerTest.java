package com.petproject.workflow.api;

import com.petproject.workflow.api.controllers.EmployeeController;
import com.petproject.workflow.api.dtos.EmployeeDto;
import com.petproject.workflow.api.dtos.EmployeeMapper;
import com.petproject.workflow.api.dtos.PositionDto;
import com.petproject.workflow.store.entities.Employee;
import com.petproject.workflow.store.repositories.EmployeeRepository;
import com.petproject.workflow.store.entities.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeController employeeController;

//    private MockMvc mockMvc;
//    private ObjectMapper objectMapper = new ObjectMapper();

//    @Test
//    void testGetAllEmployees_ShouldReturnListOfEmployeeDtos() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//
//        UUID id1 = UUID.randomUUID();
//        UUID id2 = UUID.randomUUID();

//        Employee employee1 = new Employee(id1, "John Doe", new Position(UUID.randomUUID(), "Developer", 2));
//        Employee employee2 = new Employee(id2, "Jane Smith", new Position(UUID.randomUUID(), "Manager", 3));
//        List<Employee> employees = Arrays.asList(employee1, employee2);
//
//        EmployeeDto dto1 = new EmployeeDto(id1, "John Doe", new PositionDto(UUID.randomUUID(), "Developer", 2));
//        EmployeeDto dto2 = new EmployeeDto(id2, "Jane Smith", new PositionDto(UUID.randomUUID(), "Manager", 3));

//        when(employeeRepository.findAll()).thenReturn(employees);
//        when(employeeMapper.mapToEmployeeDto(employee1)).thenReturn(dto1);
//        when(employeeMapper.mapToEmployeeDto(employee2)).thenReturn(dto2);

    // Act & Assert
//        mockMvc.perform(get("/api/employees/"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].id", is(id1.toString())))
//                .andExpect(jsonPath("$[0].name", is("John Doe")))
//                .andExpect(jsonPath("$[1].id", is(id2.toString())))
//                .andExpect(jsonPath("$[1].name", is("Jane Smith")));
//
//        verify(employeeRepository, times(1)).findAll();
//        verify(employeeMapper, times(1)).mapToEmployeeDto(employee1);
//        verify(employeeMapper, times(1)).mapToEmployeeDto(employee2);
//    }

//    @Test
//    void testGetAllEmployees_WhenNoEmployees_ShouldReturnEmptyList() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//
//        when(employeeRepository.findAll()).thenReturn(Arrays.asList());
//
//        // Act & Assert
//        mockMvc.perform(get("/api/employees/"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(0)));
//
//        verify(employeeRepository, times(1)).findAll();
//        verify(employeeMapper, never()).mapToEmployeeDto(any());
//    }
//
//    @Test
//    void testGetEmployee_WithExistingId_ShouldReturnEmployeeDto() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//
//        UUID id = UUID.randomUUID();
//        Employee employee = new Employee(id, "John Doe", new Position(UUID.randomUUID(), "Developer", 2));
//        EmployeeDto dto = new EmployeeDto(id, "John Doe", new PositionDto(UUID.randomUUID(), "Developer", 2));
//
//        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
//        when(employeeMapper.mapToEmployeeDto(employee)).thenReturn(dto);
//
//        // Act & Assert
//        mockMvc.perform(get("/api/employees/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id", is(id.toString())))
//                .andExpect(jsonPath("$.name", is("John Doe")));
//
//        verify(employeeRepository, times(1)).findById(id);
//        verify(employeeMapper, times(1)).mapToEmployeeDto(employee);
//    }
//
//    @Test
//    void testGetEmployee_WithNonExistingId_ShouldReturnNotFound() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//
//        UUID id = UUID.randomUUID();
//
//        when(employeeRepository.findById(id)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        mockMvc.perform(get("/api/employees/{id}", id))
//                .andExpect(status().isNotFound());
//
//        verify(employeeRepository, times(1)).findById(id);
//        verify(employeeMapper, never()).mapToEmployeeDto(any());
//    }
//
//    @Test
//    void testGetEmployee_WithInvalidUuid_ShouldReturnBadRequest() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//
//        // Act & Assert
//        mockMvc.perform(get("/api/employees/{id}", "invalid-uuid"))
//                .andExpect(status().isBadRequest());
//
//        verify(employeeRepository, never()).findById(any());
//        verify(employeeMapper, never()).mapToEmployeeDto(any());
//    }
//
//    @Test
//    void testSaveEmployee_WithValidDto_ShouldReturnCreatedEmployeeDto() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//
//        UUID id = UUID.randomUUID();
//        UUID positionId = UUID.randomUUID();
//
//        PositionDto positionDto = new PositionDto(positionId, "Developer", 2);
//        EmployeeDto requestDto = new EmployeeDto(null, "John Doe", positionDto);
//
//        Position position = new Position(positionId, "Developer", 2);
//        Employee employeeToSave = new Employee(null, "John Doe", position);
//        Employee savedEmployee = new Employee(id, "John Doe", position);
//        EmployeeDto responseDto = new EmployeeDto(id, "John Doe", positionDto);
//
//        when(employeeMapper.mapToEmployee(any(EmployeeDto.class))).thenReturn(employeeToSave);
//        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);
//        when(employeeMapper.mapToEmployeeDto(any(Employee.class))).thenReturn(responseDto);
//
//        // Act & Assert
//        mockMvc.perform(post("/api/employees")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto)))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id", is(id.toString())))
//                .andExpect(jsonPath("$.name", is("John Doe")))
//                .andExpect(jsonPath("$.position.name", is("Developer")));
//
//        verify(employeeRepository, times(1)).save(employeeToSave);
//        verify(employeeMapper, times(1)).mapToEmployeeDto(savedEmployee);
//    }
//
//    @Test
//    void testSaveEmployee_WithInvalidDto_ShouldReturnBadRequest() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//
//        EmployeeDto invalidDto = new EmployeeDto(null, "A", null); // Short name and null position
//
//        // Act & Assert
//        mockMvc.perform(post("/api/employees")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidDto)))
//                .andExpect(status().isBadRequest());
//
//        verify(employeeMapper, never()).mapToEmployee(any());
//        verify(employeeRepository, never()).save(any());
//    }
//
//    @Test
//    void testSaveEmployee_WithNullPosition_ShouldReturnBadRequest() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//
//        EmployeeDto invalidDto = new EmployeeDto(null, "John Doe", null);
//
//        // Act & Assert
//        mockMvc.perform(post("/api/employees")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidDto)))
//                .andExpect(status().isBadRequest());
//
//        verify(employeeMapper, never()).mapToEmployee(any());
//        verify(employeeRepository, never()).save(any());
//    }
//
//    @Test
//    void testGetSubordinateEmployees_WithExistingId_ShouldReturnSubordinates() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//
//        UUID managerId = UUID.randomUUID();
//        UUID subordinateId = UUID.randomUUID();
//
//        Employee subordinate = new Employee(subordinateId, "Subordinate", new Position(UUID.randomUUID(), "Developer", 2));
//        List<Employee> subordinates = Arrays.asList(subordinate);
//
//        EmployeeDto subordinateDto = new EmployeeDto(subordinateId, "Subordinate", new PositionDto(UUID.randomUUID(), "Developer", 2));
//
//        when(employeeRepository.findAllEmployeesWithLowerPositionLevel(managerId)).thenReturn(subordinates);
//        when(employeeMapper.mapToEmployeeDto(subordinate)).thenReturn(subordinateDto);
//
//        // Act & Assert
//        mockMvc.perform(get("/api/employees/subordinate/{id}", managerId))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].id", is(subordinateId.toString())))
//                .andExpect(jsonPath("$[0].name", is("Subordinate")));
//
//        verify(employeeRepository, times(1)).findAllEmployeesWithLowerPositionLevel(managerId);
//        verify(employeeMapper, times(1)).mapToEmployeeDto(subordinate);
//    }
//
//    @Test
//    void testGetSubordinateEmployees_WithNoSubordinates_ShouldReturnEmptyList() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//
//        UUID managerId = UUID.randomUUID();
//
//        when(employeeRepository.findAllEmployeesWithLowerPositionLevel(managerId)).thenReturn(Arrays.asList());
//
//        // Act & Assert
//        mockMvc.perform(get("/api/employees/subordinate/{id}", managerId))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(0)));
//
//        verify(employeeRepository, times(1)).findAllEmployeesWithLowerPositionLevel(managerId);
//        verify(employeeMapper, never()).mapToEmployeeDto(any());
//    }
//
//    @Test
//    void testGetBatchEmployees_WithValidUuids_ShouldReturnEmployees() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//
//        UUID id1 = UUID.randomUUID();
//        UUID id2 = UUID.randomUUID();
//        List<UUID> uuids = Arrays.asList(id1, id2);
//
//        Employee employee1 = new Employee(id1, "John Doe", new Position(UUID.randomUUID(), "Developer", 2));
//        Employee employee2 = new Employee(id2, "Jane Smith", new Position(UUID.randomUUID(), "Manager", 3));
//        List<Employee> employees = Arrays.asList(employee1, employee2);
//
//        EmployeeDto dto1 = new EmployeeDto(id1, "John Doe", new PositionDto(UUID.randomUUID(), "Developer", 2));
//        EmployeeDto dto2 = new EmployeeDto(id2, "Jane Smith", new PositionDto(UUID.randomUUID(), "Manager", 3));
//
//        when(employeeRepository.findAllById(uuids)).thenReturn(employees);
//        when(employeeMapper.mapToEmployeeDto(employee1)).thenReturn(dto1);
//        when(employeeMapper.mapToEmployeeDto(employee2)).thenReturn(dto2);
//
//        // Act & Assert
//        mockMvc.perform(post("/api/employees/batch")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(uuids)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].id", is(id1.toString())))
//                .andExpect(jsonPath("$[1].id", is(id2.toString())));
//
//        verify(employeeRepository, times(1)).findAllById(uuids);
//        verify(employeeMapper, times(1)).mapToEmployeeDto(employee1);
//        verify(employeeMapper, times(1)).mapToEmployeeDto(employee2);
//    }
//
//    @Test
//    void testGetBatchEmployees_WithEmptyList_ShouldReturnEmptyList() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//
//        List<UUID> uuids = Arrays.asList();
//
//        when(employeeRepository.findAllById(uuids)).thenReturn(Arrays.asList());
//
//        // Act & Assert
//        mockMvc.perform(post("/api/employees/batch")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(uuids)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(0)));
//
//        verify(employeeRepository, times(1)).findAllById(uuids);
//        verify(employeeMapper, never()).mapToEmployeeDto(any());
//    }
//
//    @Test
//    void testGetBatchEmployees_WithSomeNonExistingIds_ShouldReturnOnlyExisting() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//
//        UUID existingId = UUID.randomUUID();
//        UUID nonExistingId = UUID.randomUUID();
//        List<UUID> uuids = Arrays.asList(existingId, nonExistingId);
//
//        Employee existingEmployee = new Employee(existingId, "John Doe", new Position(UUID.randomUUID(), "Developer", 2));
//        List<Employee> employees = Arrays.asList(existingEmployee);
//
//        EmployeeDto dto = new EmployeeDto(existingId, "John Doe", new PositionDto(UUID.randomUUID(), "Developer", 2));
//
//        when(employeeRepository.findAllById(uuids)).thenReturn(employees);
//        when(employeeMapper.mapToEmployeeDto(existingEmployee)).thenReturn(dto);
//
//        // Act & Assert
//        mockMvc.perform(post("/api/employees/batch")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(uuids)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].id", is(existingId.toString())));
//
//        verify(employeeRepository, times(1)).findAllById(uuids);
//        verify(employeeMapper, times(1)).mapToEmployeeDto(existingEmployee);
//    }
//
//    @Test
//    void testGetBatchEmployees_WithInvalidJson_ShouldReturnBadRequest() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//
//        // Act & Assert
//        mockMvc.perform(post("/api/employees/batch")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//
//        verify(employeeRepository, never()).findAllById(any());
//        verify(employeeMapper, never()).mapToEmployeeDto(any());
//    }
//
//    @Test
//    void testGetBatchEmployees_WithWrongContentType_ShouldReturnUnsupportedMediaType() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//
//        List<UUID> uuids = Arrays.asList(UUID.randomUUID());
//
//        // Act & Assert
//        mockMvc.perform(post("/api/employees/batch")
//                        .contentType(MediaType.TEXT_PLAIN)
//                        .content(objectMapper.writeValueAsString(uuids)))
//                .andExpect(status().isUnsupportedMediaType());
//
//        verify(employeeRepository, never()).findAllById(any());
//        verify(employeeMapper, never()).mapToEmployeeDto(any());
//    }
//
//    @Test
//    void testControllerEndpoints_ShouldHaveCorrectPaths() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
//
//        // Test GET all endpoint
//        when(employeeRepository.findAll()).thenReturn(Arrays.asList());
//        mockMvc.perform(get("/api/employees/"))
//                .andExpect(status().isOk());
//
//        // Test GET by ID endpoint
//        UUID testId = UUID.randomUUID();
//        when(employeeRepository.findById(testId)).thenReturn(Optional.empty());
//        mockMvc.perform(get("/api/employees/{id}", testId))
//                .andExpect(status().isNotFound());
//
//        // Test GET subordinates endpoint
//        when(employeeRepository.findAllEmployeesWithLowerPositionLevel(testId)).thenReturn(Arrays.asList());
//        mockMvc.perform(get("/api/employees/subordinate/{id}", testId))
//                .andExpect(status().isOk());
//
//        // Test POST batch endpoint
//        when(employeeRepository.findAllById(any())).thenReturn(Arrays.asList());
//        mockMvc.perform(post("/api/employees/batch")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("[]"))
//                .andExpect(status().isOk());
//    }
}