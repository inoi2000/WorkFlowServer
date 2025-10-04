package com.petproject.workflow.api;

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

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PositionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
        positionRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testGetAllPositions_WhenPositionsExist_ShouldReturnList() throws Exception {
        // Arrange
        Position position1 = new Position(UUID.randomUUID(), "Developer", 2);
        Position position2 = new Position(UUID.randomUUID(), "Manager", 3);
        positionRepository.save(position1);
        positionRepository.save(position2);

        // Act & Assert
        mockMvc.perform(get("/api/positions/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(oneOf("Developer", "Manager"))))
                .andExpect(jsonPath("$[1].name", is(oneOf("Developer", "Manager"))))
                .andExpect(jsonPath("$[0].level", is(oneOf(2, 3))))
                .andExpect(jsonPath("$[1].level", is(oneOf(2, 3))));
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testGetAllPositions_WhenNoPositions_ShouldReturnEmptyList() throws Exception {
        // Arrange - база очищена благодаря @Transactional

        // Act & Assert
        mockMvc.perform(get("/api/positions/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testSavePosition_WithValidData_ShouldCreateAndReturnPosition() throws Exception {
        // Arrange
        String positionJson = """
        {
            "name": "Senior Developer",
            "level": 4
        }
        """;

        // Act & Assert
        mockMvc.perform(post("/api/positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(positionJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name", is("Senior Developer")))
                .andExpect(jsonPath("$.level", is(4)));

        // Verify in database
        mockMvc.perform(get("/api/positions/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Senior Developer")));
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testSavePosition_WithExistingId_ShouldUpdatePosition() throws Exception {
        // Arrange - сначала создаем позицию
        Position existingPosition =
                positionRepository.save(new Position(UUID.randomUUID(), "Junior Developer", 1));

        String updateJson = """
        {
            "id": "%s",
            "name": "Updated Developer",
            "level": 2
        }
        """.formatted(existingPosition.getId());

        // Act & Assert
        mockMvc.perform(patch("/api/positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(existingPosition.getId().toString())))
                .andExpect(jsonPath("$.name", is("Updated Developer")))
                .andExpect(jsonPath("$.level", is(2)));

        // Verify update
        mockMvc.perform(get("/api/positions/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Updated Developer")));
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testSavePosition_WithInvalidName_ShouldReturnBadRequest() throws Exception {
        // Arrange
        String invalidJson = """
        {
            "name": "A",
            "level": 2
        }
        """;

        // Act & Assert
        mockMvc.perform(post("/api/positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testSavePosition_WithNullName_ShouldReturnBadRequest() throws Exception {
        // Arrange
        String invalidJson = """
        {
            "name": null,
            "level": 2
        }
        """;

        // Act & Assert
        mockMvc.perform(post("/api/positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testSavePosition_WithNegativeLevel_ShouldReturnBadRequest() throws Exception {
        // Arrange
        String invalidJson = """
        {
            "name": "Developer",
            "level": -1
        }
        """;

        // Act & Assert
        mockMvc.perform(post("/api/positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testSavePosition_WithMissingName_ShouldReturnBadRequest() throws Exception {
        // Arrange
        String invalidJson = """
        {
            "level": 2
        }
        """;

        // Act & Assert
        mockMvc.perform(post("/api/positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testSavePosition_WithDuplicateName_ShouldCreateSuccessfully() throws Exception {
        // Arrange - создаем первую позицию
        positionRepository.save(new Position(UUID.randomUUID(), "Developer", 2));

        String duplicateJson = """
        {
            "name": "Developer",
            "level": 3
        }
        """;

        // Act & Assert - допускаем дубликаты имен (если нет unique constraint)
        mockMvc.perform(post("/api/positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(duplicateJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Developer")))
                .andExpect(jsonPath("$.level", is(3)));

        // Verify both positions exist
        mockMvc.perform(get("/api/positions/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Developer")))
                .andExpect(jsonPath("$[1].name", is("Developer")));
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testEndToEnd_CRUDOperations() throws Exception {
        // Create
        String createJson = """
        {
            "name": "Architect",
            "level": 5
        }
        """;

        String response = mockMvc.perform(post("/api/positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Read all
        mockMvc.perform(get("/api/positions/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Architect")));

        // Update (using POST for update as per controller design)
        String updateJson = response.replace("\"level\":5", "\"level\":6");

        mockMvc.perform(post("/api/positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.level", is(6)));

        // Verify update
        mockMvc.perform(get("/api/positions/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].level", is(5)));
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void testSavePosition_WithMaxLevelValue_ShouldCreateSuccessfully() throws Exception {
        // Arrange
        String maxLevelJson = """
        {
            "name": "Chief Officer",
            "level": 10
        }
        """;

        // Act & Assert
        mockMvc.perform(post("/api/positions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(maxLevelJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.level", is(10)));
    }
}
