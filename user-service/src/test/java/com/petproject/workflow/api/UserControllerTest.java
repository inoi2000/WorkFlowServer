package com.petproject.workflow.api;

import com.petproject.workflow.store.Role;
import com.petproject.workflow.store.User;
import com.petproject.workflow.store.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    private User validUser;
    private User invalidUser;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        validUser = User.builder()
                .id(UUID.randomUUID())
                .username("validuser")
                .password("validpassword123")
                .email("user@example.com")
                .role(Role.ROLE_DRIVER)
                .build();

        invalidUser = User.builder()
                .id(UUID.randomUUID())
                .username("user") // слишком короткий
                .password("pass") // слишком короткий
                .email("invalid-email")
                .role(Role.ROLE_DRIVER)
                .build();
    }

    @Test
    void findAll_ShouldReturnAllUsers() {
        // Arrange
        List<User> expectedUsers = Arrays.asList(validUser);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        Iterable<User> result = userController.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(expectedUsers, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findAll_WhenNoUsers_ShouldReturnEmptyList() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        Iterable<User> result = userController.findAll();

        // Assert
        assertNotNull(result);
        assertFalse(result.iterator().hasNext());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUser_WithExistingUsername_ShouldReturnUser() {
        // Arrange
        String username = "validuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(validUser));

        // Act
        ResponseEntity<User> response = userController.getUser(username);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(validUser, response.getBody());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void getUser_WithNonExistingUsername_ShouldReturnNotFound() {
        // Arrange
        String username = "nonexisting";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = userController.getUser(username);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void getUser_WithNullUsername_ShouldReturnNotFound() {
        // Arrange
        when(userRepository.findByUsername(null)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = userController.getUser(null);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userRepository, times(1)).findByUsername(null);
    }

    @Test
    void saveUser_WithValidUser_ShouldSaveAndReturnUser() {
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(validUser);

        // Act
        User result = userController.saveUser(validUser).getBody();

        // Assert
        assertNotNull(result);
        assertEquals(validUser, result);
        verify(userRepository, times(1)).save(validUser);
    }

    @Test
    void saveUser_WithNullUser_ShouldThrowException() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () -> {
            userController.saveUser(null);
        });
    }

    @Test
    void getRoles_ShouldReturnAllRoleNames() {
        // Arrange
        String[] expectedRoles = {
                "ROLE_DIRECTOR",
                "ROLE_HR",
                "ROLE_DRIVER",
                "ROLE_ADMIN"
        };

        // Act
        String[] result = userController.getRoles();

        // Assert
        assertNotNull(result);
        assertArrayEquals(expectedRoles, result);
        assertEquals(Role.values().length, result.length);
    }

    @Test
    void userValidation_ValidUser_ShouldPassValidation() {
        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(validUser);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void userValidation_InvalidUsername_ShouldFailValidation() {
        // Arrange
        User userWithShortUsername = User.builder()
                .username("user") // 4 characters
                .password("validpassword123")
                .email("user@example.com")
                .role(Role.ROLE_DRIVER)
                .build();

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(userWithShortUsername);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("username")));
    }

    @Test
    void userValidation_InvalidPassword_ShouldFailValidation() {
        // Arrange
        User userWithShortPassword = User.builder()
                .username("validuser")
                .password("pass") // 4 characters
                .email("user@example.com")
                .role(Role.ROLE_DRIVER)
                .build();

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(userWithShortPassword);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }
}