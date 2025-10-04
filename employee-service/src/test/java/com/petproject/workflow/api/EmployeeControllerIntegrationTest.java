package com.petproject.workflow.api;

import com.petproject.workflow.store.entities.Employee;
import com.petproject.workflow.store.repositories.EmployeeRepository;
import com.petproject.workflow.store.entities.Position;
import com.petproject.workflow.store.repositories.PositionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    private Position developerPosition;
    private Position managerPosition;
    private Position directorPosition;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
        positionRepository.deleteAll();
        // Создаем тестовые позиции
        developerPosition = positionRepository.save(new Position(UUID.randomUUID(), "Developer", 2));
        managerPosition = positionRepository.save(new Position(UUID.randomUUID(), "Manager", 3));
        directorPosition = positionRepository.save(new Position(UUID.randomUUID(), "Director", 4));
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testGetAllEmployees_WhenEmployeesExist_ShouldReturnList() throws Exception {
        // Arrange
        Employee employee1 = new Employee(UUID.randomUUID(), "John Doe", developerPosition);
        Employee employee2 = new Employee(UUID.randomUUID(), "Jane Smith", managerPosition);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // Act & Assert
        mockMvc.perform(get("/api/employees/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(oneOf("John Doe", "Jane Smith"))))
                .andExpect(jsonPath("$[1].name", is(oneOf("John Doe", "Jane Smith"))))
                .andExpect(jsonPath("$[0].position.name", is(oneOf("Developer", "Manager"))));
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testGetAllEmployees_WhenNoEmployees_ShouldReturnEmptyList() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/employees/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testGetEmployee_WithExistingId_ShouldReturnEmployee() throws Exception {
        // Arrange
        Employee employee = employeeRepository.save(new Employee(UUID.randomUUID(), "John Doe", developerPosition));

        // Act & Assert
        mockMvc.perform(get("/api/employees/{id}", employee.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(employee.getId().toString())))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.position.name", is("Developer")));
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testGetEmployee_WithNonExistingId_ShouldReturnNotFound() throws Exception {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();

        // Act & Assert
        mockMvc.perform(get("/api/employees/{id}", nonExistingId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testGetEmployee_WithInvalidUuid_ShouldReturnBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/employees/{id}", "invalid-uuid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testSaveEmployee_WithValidData_ShouldCreateEmployee() throws Exception {
        // Arrange
        String employeeJson = """
                {
                    "name": "Alice Johnson",
                    "position": {
                        "id": "%s",
                        "name": "Developer",
                        "level": 2
                    }
                }
                """.formatted(developerPosition.getId());

        // Act & Assert
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is("Alice Johnson")))
                .andExpect(jsonPath("$.position.name", is("Developer")));

        // Verify in database
        mockMvc.perform(get("/api/employees/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Alice Johnson")));
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testSaveEmployee_WithInvalidName_ShouldReturnBadRequest() throws Exception {
        // Arrange
        String invalidJson = """
                {
                    "name": "A",
                    "position": {
                        "id": "%s",
                        "name": "Developer",
                        "level": 2
                    }
                }
                """.formatted(developerPosition.getId());

        // Act & Assert
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testSaveEmployee_WithNullPosition_ShouldReturnBadRequest() throws Exception {
        // Arrange
        String invalidJson = """
                {
                    "name": "John Doe",
                    "position": null
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testGetSubordinateEmployees_WithManager_ShouldReturnSubordinates() throws Exception {
        // Arrange
        Employee manager = employeeRepository.save(new Employee(UUID.randomUUID(), "Manager", managerPosition));
        Employee subordinate1 = employeeRepository.save(new Employee(UUID.randomUUID(), "Subordinate1", developerPosition));
        Employee subordinate2 = employeeRepository.save(new Employee(UUID.randomUUID(), "Subordinate2", developerPosition));

        // Act & Assert
        mockMvc.perform(get("/api/employees/subordinate/{id}", manager.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(oneOf("Subordinate1", "Subordinate2"))))
                .andExpect(jsonPath("$[1].name", is(oneOf("Subordinate1", "Subordinate2"))))
                .andExpect(jsonPath("$[0].position.level", is(2)))
                .andExpect(jsonPath("$[1].position.level", is(2)));
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testGetSubordinateEmployees_WithNoSubordinates_ShouldReturnEmptyList() throws Exception {
        // Arrange
        Employee director = employeeRepository.save(new Employee(UUID.randomUUID(), "Director", directorPosition));

        // Act & Assert
        mockMvc.perform(get("/api/employees/subordinate/{id}", director.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testGetSubordinateEmployees_WithLowLevelPosition_ShouldReturnEmptyList() throws Exception {
        // Arrange
        Employee developer = employeeRepository.save(new Employee(UUID.randomUUID(), "Developer", developerPosition));

        // Act & Assert
        mockMvc.perform(get("/api/employees/subordinate/{id}", developer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(username = "test-user", authorities = {"INTERNAL"})
    void testGetBatchEmployees_WithValidUuids_ShouldReturnEmployees() throws Exception {
        // Arrange
        Employee employee1 = employeeRepository.save(new Employee(UUID.randomUUID(), "Employee1", developerPosition));
        Employee employee2 = employeeRepository.save(new Employee(UUID.randomUUID(), "Employee2", managerPosition));
        Employee employee3 = employeeRepository.save(new Employee(UUID.randomUUID(), "Employee3", directorPosition));

        List<UUID> uuids = Arrays.asList(employee1.getId(), employee2.getId(), employee3.getId());

        // Act & Assert
        mockMvc.perform(post("/api/employees/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                ["%s", "%s", "%s"]
                                """.formatted(employee1.getId(), employee2.getId(), employee3.getId())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(oneOf(
                        employee1.getId().toString(),
                        employee2.getId().toString(),
                        employee3.getId().toString()
                ))));
    }

    @Test
    @WithMockUser(username = "test-user", authorities = {"INTERNAL"})
    void testGetBatchEmployees_WithSomeNonExistingIds_ShouldReturnOnlyExisting() throws Exception {
        // Arrange
        Employee existingEmployee = employeeRepository.save(new Employee(UUID.randomUUID(), "Existing", developerPosition));
        UUID nonExistingId = UUID.randomUUID();

        // Act & Assert
        mockMvc.perform(post("/api/employees/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                ["%s", "%s"]
                                """.formatted(existingEmployee.getId(), nonExistingId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(existingEmployee.getId().toString())))
                .andExpect(jsonPath("$[0].name", is("Existing")));
    }

    @Test
    @WithMockUser(username = "test-user", authorities = {"INTERNAL"})
    void testGetBatchEmployees_WithEmptyList_ShouldReturnEmptyList() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/employees/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(username = "test-user", authorities = {"INTERNAL"})
    void testGetBatchEmployees_WithAllNonExistingIds_ShouldReturnEmptyList() throws Exception {
        // Arrange
        UUID nonExistingId1 = UUID.randomUUID();
        UUID nonExistingId2 = UUID.randomUUID();

        // Act & Assert
        mockMvc.perform(post("/api/employees/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                ["%s", "%s"]
                                """.formatted(nonExistingId1, nonExistingId2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(username = "test-user", authorities = {"INTERNAL"})
    void testGetBatchEmployees_WithInvalidJson_ShouldReturnBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/employees/batch")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testEndToEnd_CRUDOperations() throws Exception {
        // Create
        String createJson = """
                {
                    "name": "Test Employee",
                    "position": {
                        "id": "%s",
                        "name": "Developer",
                        "level": 2
                    }
                }
                """.formatted(developerPosition.getId());

        String response = mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Read all
        mockMvc.perform(get("/api/employees/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Test Employee")));

        // Extract ID from response for update
        String id = response.split("\"id\":\"")[1].split("\"")[0];

        // Update
        String updateJson = """
                {
                    "id": "%s",
                    "name": "Updated Employee",
                    "position": {
                        "id": "%s",
                        "name": "Manager",
                        "level": 3
                    }
                }
                """.formatted(id, managerPosition.getId());

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Updated Employee")));

        // Verify update
        mockMvc.perform(get("/api/employees/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Employee")));
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testSaveEmployee_WithPositionWithoutId_ShouldCreateNewPosition() throws Exception {
        // Arrange
        String employeeJson = """
                {
                    "name": "New Employee",
                    "position": {
                        "name": "New Position",
                        "level": 5
                    }
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("New Employee")))
                .andExpect(jsonPath("$.position.name", is("New Position")))
                .andExpect(jsonPath("$.position.level", is(5)));
    }

    @Test
    @WithMockUser(username = "test-user", authorities = {"INTERNAL"})
    void testComplexScenario_MultipleOperations() throws Exception {
        // Create multiple employees with different positions
        Employee dev1 = employeeRepository.save(new Employee(UUID.randomUUID(), "Dev1", developerPosition));
        Employee dev2 = employeeRepository.save(new Employee(UUID.randomUUID(), "Dev2", developerPosition));
        Employee manager = employeeRepository.save(new Employee(UUID.randomUUID(), "Manager", managerPosition));

        // Test get all
        mockMvc.perform(get("/api/employees/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        // Test get batch
        mockMvc.perform(post("/api/employees/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                ["%s", "%s"]
                                """.formatted(dev1.getId(), manager.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        // Test get subordinates for manager
        mockMvc.perform(get("/api/employees/subordinate/{id}", manager.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].position.level", is(2)))
                .andExpect(jsonPath("$[1].position.level", is(2)));
    }
}
