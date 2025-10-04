package com.petproject.workflow.api;

import com.petproject.workflow.api.dtos.EmployeeDto;
import com.petproject.workflow.api.dtos.EmployeeMapper;
import com.petproject.workflow.api.dtos.PositionDto;
import com.petproject.workflow.api.dtos.PositionMapper;
import com.petproject.workflow.store.entities.Employee;
import com.petproject.workflow.store.entities.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeMapperTest {

    @Mock
    private PositionMapper positionMapper;

    @InjectMocks
    private EmployeeMapper employeeMapper;

    @Test
    void testMapToEmployee_WithValidDto_ShouldReturnCorrectEmployee() {
        // Arrange
        UUID employeeId = UUID.randomUUID();
        String employeeName = "John Doe";

        UUID positionId = UUID.randomUUID();
        PositionDto positionDto = new PositionDto(positionId, "Developer", 2);
        Position mappedPosition = new Position(positionId, "Developer", 2);

        EmployeeDto employeeDto = new EmployeeDto(employeeId, employeeName, positionDto);

        when(positionMapper.mapToPosition(any(PositionDto.class))).thenReturn(mappedPosition);

        // Act
        Employee result = employeeMapper.mapToEmployee(employeeDto);

        // Assert
        assertNotNull(result);
        assertEquals(employeeId, result.getId());
        assertEquals(employeeName, result.getName());
        assertEquals(mappedPosition, result.getPosition());

        verify(positionMapper, times(1)).mapToPosition(positionDto);
    }

    @Test
    void testMapToEmployee_WithNullDto_ShouldThrowException() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () -> {
            employeeMapper.mapToEmployee(null);
        });

        verify(positionMapper, never()).mapToPosition(any());
    }

    @Test
    void testMapToEmployee_WithNullId_ShouldReturnEmployeeWithNullId() {
        // Arrange
        UUID positionId = UUID.randomUUID();
        PositionDto positionDto = new PositionDto(positionId, "Manager", 3);
        Position mappedPosition = new Position(positionId, "Manager", 3);

        EmployeeDto employeeDto = new EmployeeDto(null, "Jane Smith", positionDto);

        when(positionMapper.mapToPosition(any(PositionDto.class))).thenReturn(mappedPosition);

        // Act
        Employee result = employeeMapper.mapToEmployee(employeeDto);

        // Assert
        assertNotNull(result);
        assertNull(result.getId());
        assertEquals("Jane Smith", result.getName());
        assertEquals(mappedPosition, result.getPosition());

        verify(positionMapper, times(1)).mapToPosition(positionDto);
    }

    @Test
    void testMapToEmployeeDto_WithValidEmployee_ShouldReturnCorrectDto() {
        // Arrange
        UUID employeeId = UUID.randomUUID();
        String employeeName = "Alice Johnson";

        UUID positionId = UUID.randomUUID();
        Position position = new Position(positionId, "Designer", 1);
        PositionDto mappedPositionDto = new PositionDto(positionId, "Designer", 1);

        Employee employee = new Employee(employeeId, employeeName, position);

        when(positionMapper.mapToPositionDto(any(Position.class))).thenReturn(mappedPositionDto);

        // Act
        EmployeeDto result = employeeMapper.mapToEmployeeDto(employee);

        // Assert
        assertNotNull(result);
        assertEquals(employeeId, result.getId());
        assertEquals(employeeName, result.getName());
        assertEquals(mappedPositionDto, result.getPosition());

        verify(positionMapper, times(1)).mapToPositionDto(position);
    }

    @Test
    void testMapToEmployeeDto_WithNullEmployee_ShouldThrowException() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () -> {
            employeeMapper.mapToEmployeeDto(null);
        });

        verify(positionMapper, never()).mapToPositionDto(any());
    }

    @Test
    void testMapToEmployeeDto_WithNullId_ShouldReturnDtoWithNullId() {
        // Arrange
        UUID positionId = UUID.randomUUID();
        Position position = new Position(positionId, "Analyst", 2);
        PositionDto mappedPositionDto = new PositionDto(positionId, "Analyst", 2);

        Employee employee = new Employee(null, "Charlie Wilson", position);

        when(positionMapper.mapToPositionDto(any(Position.class))).thenReturn(mappedPositionDto);

        // Act
        EmployeeDto result = employeeMapper.mapToEmployeeDto(employee);

        // Assert
        assertNotNull(result);
        assertNull(result.getId());
        assertEquals("Charlie Wilson", result.getName());
        assertEquals(mappedPositionDto, result.getPosition());

        verify(positionMapper, times(1)).mapToPositionDto(position);
    }

    @Test
    void testBidirectionalMapping_ShouldPreserveAllFields() {
        // Arrange
        UUID employeeId = UUID.randomUUID();
        String employeeName = "David Miller";

        UUID positionId = UUID.randomUUID();
        Position originalPosition = new Position(positionId, "Director", 4);
        PositionDto positionDto = new PositionDto(positionId, "Director", 4);
        Position mappedPosition = new Position(positionId, "Director", 4);

        Employee originalEmployee = new Employee(employeeId, employeeName, originalPosition);

        when(positionMapper.mapToPositionDto(any(Position.class))).thenReturn(positionDto);
        when(positionMapper.mapToPosition(any(PositionDto.class))).thenReturn(mappedPosition);

        // Act
        EmployeeDto dto = employeeMapper.mapToEmployeeDto(originalEmployee);
        Employee mappedEmployee = employeeMapper.mapToEmployee(dto);

        // Assert
        assertNotNull(mappedEmployee);
        assertEquals(employeeId, mappedEmployee.getId());
        assertEquals(employeeName, mappedEmployee.getName());
        assertEquals(mappedPosition, mappedEmployee.getPosition());

        verify(positionMapper, times(1)).mapToPositionDto(originalPosition);
        verify(positionMapper, times(1)).mapToPosition(positionDto);
    }

    @Test
    void testMapToEmployee_WithEmptyName_ShouldReturnEmployeeWithEmptyName() {
        // Arrange
        UUID positionId = UUID.randomUUID();
        PositionDto positionDto = new PositionDto(positionId, "Developer", 2);
        Position mappedPosition = new Position(positionId, "Developer", 2);

        EmployeeDto employeeDto = new EmployeeDto(UUID.randomUUID(), "", positionDto);

        when(positionMapper.mapToPosition(any(PositionDto.class))).thenReturn(mappedPosition);

        // Act
        Employee result = employeeMapper.mapToEmployee(employeeDto);

        // Assert
        assertNotNull(result);
        assertEquals("", result.getName());
        assertEquals(mappedPosition, result.getPosition());

        verify(positionMapper, times(1)).mapToPosition(positionDto);
    }

    @Test
    void testMapToEmployeeDto_WithEmptyName_ShouldReturnDtoWithEmptyName() {
        // Arrange
        UUID positionId = UUID.randomUUID();
        Position position = new Position(positionId, "Manager", 3);
        PositionDto mappedPositionDto = new PositionDto(positionId, "Manager", 3);

        Employee employee = new Employee(UUID.randomUUID(), "", position);

        when(positionMapper.mapToPositionDto(any(Position.class))).thenReturn(mappedPositionDto);

        // Act
        EmployeeDto result = employeeMapper.mapToEmployeeDto(employee);

        // Assert
        assertNotNull(result);
        assertEquals("", result.getName());
        assertEquals(mappedPositionDto, result.getPosition());

        verify(positionMapper, times(1)).mapToPositionDto(position);
    }

    @Test
    void testPositionMapperInteraction_ShouldBeCalledExactlyOnce() {
        // Arrange
        UUID employeeId = UUID.randomUUID();
        UUID positionId = UUID.randomUUID();

        PositionDto positionDto = new PositionDto(positionId, "Test", 1);
        Position mappedPosition = new Position(positionId, "Test", 1);
        EmployeeDto employeeDto = new EmployeeDto(employeeId, "Test", positionDto);

        when(positionMapper.mapToPosition(any(PositionDto.class))).thenReturn(mappedPosition);

        // Act
        employeeMapper.mapToEmployee(employeeDto);

        // Assert
        verify(positionMapper, times(1)).mapToPosition(positionDto);
        verifyNoMoreInteractions(positionMapper);
    }
}