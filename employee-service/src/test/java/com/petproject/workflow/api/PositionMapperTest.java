package com.petproject.workflow.api;

import com.petproject.workflow.api.dtos.PositionDto;
import com.petproject.workflow.api.dtos.PositionMapper;
import com.petproject.workflow.store.entities.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PositionMapperTest {

    @InjectMocks
    private PositionMapper positionMapper;

    @Test
    void testMapToPosition_WithValidDto_ShouldReturnCorrectPosition() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "Senior Developer";
        int level = 3;

        PositionDto positionDto = new PositionDto(id, name, level);

        // Act
        Position result = positionMapper.mapToPosition(positionDto);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(name, result.getName());
        assertEquals(level, result.getLevel());
    }

    @Test
    void testMapToPosition_WithNullDto_ShouldThrowException() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () -> {
            positionMapper.mapToPosition(null);
        });
    }

    @Test
    void testMapToPosition_WithNullId_ShouldReturnPositionWithNullId() {
        // Arrange
        PositionDto positionDto = new PositionDto(null, "Developer", 2);

        // Act
        Position result = positionMapper.mapToPosition(positionDto);

        // Assert
        assertNotNull(result);
        assertNull(result.getId());
        assertEquals("Developer", result.getName());
        assertEquals(2, result.getLevel());
    }

    @Test
    void testMapToPositionDto_WithValidPosition_ShouldReturnCorrectDto() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "Junior Developer";
        int level = 1;

        Position position = new Position(id, name, level);

        // Act
        PositionDto result = positionMapper.mapToPositionDto(position);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(name, result.getName());
        assertEquals(level, result.getLevel());
    }

    @Test
    void testMapToPositionDto_WithNullPosition_ShouldThrowException() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () -> {
            positionMapper.mapToPositionDto(null);
        });
    }

    @Test
    void testMapToPositionDto_WithNullId_ShouldReturnDtoWithNullId() {
        // Arrange
        Position position = new Position(null, "Manager", 4);

        // Act
        PositionDto result = positionMapper.mapToPositionDto(position);

        // Assert
        assertNotNull(result);
        assertNull(result.getId());
        assertEquals("Manager", result.getName());
        assertEquals(4, result.getLevel());
    }

    @Test
    void testBidirectionalMapping_ShouldPreserveAllFields() {
        // Arrange
        UUID originalId = UUID.randomUUID();
        String originalName = "Architect";
        int originalLevel = 5;

        Position originalPosition = new Position(originalId, originalName, originalLevel);

        // Act
        PositionDto dto = positionMapper.mapToPositionDto(originalPosition);
        Position mappedPosition = positionMapper.mapToPosition(dto);

        // Assert
        assertNotNull(mappedPosition);
        assertEquals(originalId, mappedPosition.getId());
        assertEquals(originalName, mappedPosition.getName());
        assertEquals(originalLevel, mappedPosition.getLevel());
    }

    @Test
    void testMapToPositionDto_WithEmptyName_ShouldReturnDtoWithEmptyName() {
        // Arrange
        Position position = new Position(UUID.randomUUID(), "", 2);

        // Act
        PositionDto result = positionMapper.mapToPositionDto(position);

        // Assert
        assertNotNull(result);
        assertEquals("", result.getName());
        assertEquals(2, result.getLevel());
    }

    @Test
    void testMapToPosition_WithEmptyName_ShouldReturnPositionWithEmptyName() {
        // Arrange
        PositionDto positionDto = new PositionDto(UUID.randomUUID(), "", 2);

        // Act
        Position result = positionMapper.mapToPosition(positionDto);

        // Assert
        assertNotNull(result);
        assertEquals("", result.getName());
        assertEquals(2, result.getLevel());
    }
}