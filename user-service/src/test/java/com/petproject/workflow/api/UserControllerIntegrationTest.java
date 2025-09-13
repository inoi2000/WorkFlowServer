package com.petproject.workflow.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petproject.workflow.store.Role;
import com.petproject.workflow.store.User;
import com.petproject.workflow.store.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        testUser = User.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .password("testpassword123")
                .email("test@example.com")
                .role(Role.ROLE_DRIVER)
                .build();

        userRepository.save(testUser);
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void findAll_ShouldReturnAllUsers() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].username").value("testuser"))
                .andExpect(jsonPath("$[0].email").value("test@example.com"));
    }

    @Test
    @WithMockUser(username = "test-user", authorities = {"INTERNAL"})
    void getUser_WithExistingUsername_ShouldReturnUser() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/users/auth/testuser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.role").value("ROLE_DRIVER"));
    }

    @Test
    @WithMockUser(username = "test-user", authorities = {"INTERNAL"})
    void getUser_WithNonExistingUsername_ShouldReturnNotFound() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/users/auth/nonexisting")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void saveUser_WithValidUser_ShouldCreateUser() throws Exception {
        // Arrange
        User newUser = User.builder()
                .id(UUID.randomUUID())
                .username("newuser")
                .password("newpassword123")
                .email("new@example.com")
                .role(Role.ROLE_HR)
                .build();

        String userJson = objectMapper.writeValueAsString(newUser);

        // Act & Assert
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.email").value("new@example.com"))
                .andExpect(jsonPath("$.role").value("ROLE_HR"));

        // Verify user was saved in database
        assertTrue(userRepository.findByUsername("newuser").isPresent());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void saveUser_WithInvalidUser_ShouldReturnBadRequest() throws Exception {
        // Arrange
        User invalidUser = User.builder()
                .id(UUID.randomUUID())
                .username("user") // too short
                .password("pass") // too short
                .email("invalid-email")
                .role(Role.ROLE_DRIVER)
                .build();

        String invalidUserJson = objectMapper.writeValueAsString(invalidUser);

        // Act & Assert
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidUserJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void saveUser_WithDuplicateUsername_ShouldReturnConflict() throws Exception {
        // Arrange
        User duplicateUser = User.builder()
                .id(UUID.randomUUID())
                .username("testuser") // duplicate username
                .password("password123")
                .email("duplicate@example.com")
                .role(Role.ROLE_DRIVER)
                .build();

        String duplicateUserJson = objectMapper.writeValueAsString(duplicateUser);

        // Act & Assert
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(duplicateUserJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void saveUser_WithDuplicateEmail_ShouldReturnConflict() throws Exception {
        // Arrange
        User duplicateEmailUser = User.builder()
                .id(UUID.randomUUID())
                .username("differentuser")
                .password("password123")
                .email("test@example.com") // duplicate email
                .role(Role.ROLE_DRIVER)
                .build();

        String duplicateEmailJson = objectMapper.writeValueAsString(duplicateEmailUser);

        // Act & Assert
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(duplicateEmailJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void getRoles_ShouldReturnAllRoles() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/users/roles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0]").value("ROLE_DIRECTOR"))
                .andExpect(jsonPath("$[1]").value("ROLE_HR"))
                .andExpect(jsonPath("$[2]").value("ROLE_DRIVER"))
                .andExpect(jsonPath("$[3]").value("ROLE_ADMIN"));
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"ADMIN"})
    void findAll_WhenNoUsers_ShouldReturnEmptyArray() throws Exception {
        // Arrange
        userRepository.deleteAll();

        // Act & Assert
        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }
}