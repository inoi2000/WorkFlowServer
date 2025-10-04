package com.petproject.workflow.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petproject.workflow.api.controllers.PositionController;
import com.petproject.workflow.api.dtos.PositionDto;
import com.petproject.workflow.api.dtos.PositionMapper;
import com.petproject.workflow.store.entities.Position;
import com.petproject.workflow.store.repositories.PositionRepository;
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
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PositionControllerTest {

    @Mock
    private PositionRepository positionRepository;

    @Mock
    private PositionMapper positionMapper;

    @InjectMocks
    private PositionController positionController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

//    @Test
//    void testGetAllPositions_ShouldReturnListOfPositionDtos() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(positionController).build();
//
//        UUID id1 = UUID.randomUUID();
//        UUID id2 = UUID.randomUUID();
//
//        Position position1 = new Position(id1, "Developer", 2);
//        Position position2 = new Position(id2, "Manager", 3);
//        List<Position> positions = Arrays.asList(position1, position2);
//
//        PositionDto dto1 = new PositionDto(id1, "Developer", 2);
//        PositionDto dto2 = new PositionDto(id2, "Manager", 3);
//
//        when(positionRepository.findAll()).thenReturn(positions);
//        when(positionMapper.mapToPositionDto(position1)).thenReturn(dto1);
//        when(positionMapper.mapToPositionDto(position2)).thenReturn(dto2);
//
//        // Act & Assert
//        mockMvc.perform(get("/api/positions/"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].id", is(id1.toString())))
//                .andExpect(jsonPath("$[0].name", is("Developer")))
//                .andExpect(jsonPath("$[0].level", is(2)))
//                .andExpect(jsonPath("$[1].id", is(id2.toString())))
//                .andExpect(jsonPath("$[1].name", is("Manager")))
//                .andExpect(jsonPath("$[1].level", is(3)));
//
//        verify(positionRepository, times(1)).findAll();
//        verify(positionMapper, times(1)).mapToPositionDto(position1);
//        verify(positionMapper, times(1)).mapToPositionDto(position2);
//    }
//
//    @Test
//    void testGetAllPositions_WhenNoPositions_ShouldReturnEmptyList() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(positionController).build();
//
//        when(positionRepository.findAll()).thenReturn(Arrays.asList());
//
//        // Act & Assert
//        mockMvc.perform(get("/api/positions/"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(0)));
//
//        verify(positionRepository, times(1)).findAll();
//        verify(positionMapper, never()).mapToPositionDto(any());
//    }
//
//    @Test
//    void testSavePosition_WithInvalidDto_ShouldReturnBadRequest() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(positionController).build();
//
//        PositionDto invalidDto = new PositionDto(null, "A", 2); // Name too short
//
//        // Act & Assert
//        mockMvc.perform(post("/api/positions")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidDto)))
//                .andExpect(status().isBadRequest());
//
//        verify(positionMapper, never()).mapToPosition(any());
//        verify(positionRepository, never()).save(any());
//    }
//
//    @Test
//    void testSavePosition_WithNullName_ShouldReturnBadRequest() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(positionController).build();
//
//        PositionDto invalidDto = new PositionDto(null, null, 2); // Null name
//
//        // Act & Assert
//        mockMvc.perform(post("/api/positions")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidDto)))
//                .andExpect(status().isBadRequest());
//
//        verify(positionMapper, never()).mapToPosition(any());
//        verify(positionRepository, never()).save(any());
//    }
//
//    @Test
//    void testSavePosition_WithNegativeLevel_ShouldReturnBadRequest() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(positionController).build();
//
//        PositionDto invalidDto = new PositionDto(null, "Developer", -1); // Negative level
//
//        // Act & Assert
//        mockMvc.perform(post("/api/positions")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidDto)))
//                .andExpect(status().isBadRequest());
//
//        verify(positionMapper, never()).mapToPosition(any());
//        verify(positionRepository, never()).save(any());
//    }
//
//    @Test
//    void testSavePosition_WithEmptyJson_ShouldReturnBadRequest() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(positionController).build();
//
//        // Act & Assert
//        mockMvc.perform(post("/api/positions")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{}"))
//                .andExpect(status().isBadRequest());
//
//        verify(positionMapper, never()).mapToPosition(any());
//        verify(positionRepository, never()).save(any());
//    }
//
//    @Test
//    void testSavePosition_WithInvalidJson_ShouldReturnBadRequest() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(positionController).build();
//
//        // Act & Assert
//        mockMvc.perform(post("/api/positions")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//
//        verify(positionMapper, never()).mapToPosition(any());
//        verify(positionRepository, never()).save(any());
//    }
//
//    @Test
//    void testSavePosition_WithWrongContentType_ShouldReturnUnsupportedMediaType() throws Exception {
//        // Arrange
//        mockMvc = MockMvcBuilders.standaloneSetup(positionController).build();
//
//        PositionDto requestDto = new PositionDto(null, "Developer", 2);
//
//        // Act & Assert
//        mockMvc.perform(post("/api/positions")
//                        .contentType(MediaType.TEXT_PLAIN)
//                        .content(objectMapper.writeValueAsString(requestDto)))
//                .andExpect(status().isUnsupportedMediaType());
//
//        verify(positionMapper, never()).mapToPosition(any());
//        verify(positionRepository, never()).save(any());
//    }
}