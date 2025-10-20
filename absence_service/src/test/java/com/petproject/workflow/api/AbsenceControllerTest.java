package com.petproject.workflow.api;

import com.petproject.workflow.api.controllers.AbsenceController;
import com.petproject.workflow.store.Absence;
import com.petproject.workflow.store.AbsenceRepository;
import com.petproject.workflow.store.AbsenceStatus;
import com.petproject.workflow.store.AbsenceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AbsenceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AbsenceRepository absenceRepository;

    @InjectMocks
    private AbsenceController absenceController;

    private final UUID testId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    private final UUID employeeId = UUID.fromString("223e4567-e89b-12d3-a456-426614174000");
    private final UUID nonExistentId = UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff");

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(absenceController).build();
    }

    private Absence createTestAbsence() {
        return new Absence(
                testId,
                AbsenceType.VACATION,
                AbsenceStatus.ON_APPROVAL,
                LocalDate.of(2024, 1, 15),
                LocalDate.of(2024, 1, 20),
                "Home Office",
                true,
                employeeId
        );
    }

    private List<Absence> createTestAbsences() {
        return Arrays.asList(
                createTestAbsence(),
                new Absence(
                        UUID.fromString("323e4567-e89b-12d3-a456-426614174000"),
                        AbsenceType.SICK_LEAVE,
                        AbsenceStatus.APPROVED,
                        LocalDate.of(2024, 2, 1),
                        LocalDate.of(2024, 2, 3),
                        "Home",
                        false,
                        employeeId
                ),
                new Absence(
                        UUID.fromString("423e4567-e89b-12d3-a456-426614174000"),
                        AbsenceType.BUSINESS_TRIP,
                        AbsenceStatus.NOT_APPROVED,
                        LocalDate.of(2024, 3, 10),
                        LocalDate.of(2024, 3, 15),
                        "Client office",
                        true,
                        employeeId
                )
        );
    }

    @Test
    void getAbsenceById_ShouldReturnAbsence_WhenExists() throws Exception {
        // Arrange
        Absence testAbsence = createTestAbsence();
        given(absenceRepository.findById(testId)).willReturn(Optional.of(testAbsence));

        // Act & Assert
        mockMvc.perform(get("/api/absence/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testId.toString()))
                .andExpect(jsonPath("$.type").value("VACATION"))
                .andExpect(jsonPath("$.status").value("ON_APPROVAL"))
                .andExpect(jsonPath("$.start").value("2024-01-15"))
                .andExpect(jsonPath("$.end").value("2024-01-20"))
                .andExpect(jsonPath("$.place").value("Home Office"))
                .andExpect(jsonPath("$.isApproval").value(true))
                .andExpect(jsonPath("$.employeeId").value(employeeId.toString()));

        // Verify
        verify(absenceRepository).findById(testId);
    }

    @Test
    void getAbsenceById_ShouldReturnNotFound_WhenDoesNotExist() throws Exception {
        // Arrange
        given(absenceRepository.findById(nonExistentId)).willReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/absence/{id}", nonExistentId))
                .andExpect(status().isNotFound());

        // Verify
        verify(absenceRepository).findById(nonExistentId);
    }

    @Test
    void getAbsenceByEmployeeId_ShouldReturnAbsences_WhenEmployeeHasAbsences() throws Exception {
        // Arrange
        List<Absence> testAbsences = createTestAbsences();
        given(absenceRepository.findAllByEmployeeId(employeeId)).willReturn(testAbsences);

        // Act & Assert
        mockMvc.perform(get("/api/absence/employee/{employeeId}", employeeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value(testId.toString()))
                .andExpect(jsonPath("$[0].type").value("VACATION"))
                .andExpect(jsonPath("$[0].status").value("ON_APPROVAL"))
                .andExpect(jsonPath("$[0].employeeId").value(employeeId.toString()))
                .andExpect(jsonPath("$[1].type").value("SICK_LEAVE"))
                .andExpect(jsonPath("$[1].status").value("APPROVED"))
                .andExpect(jsonPath("$[2].type").value("BUSINESS_TRIP"))
                .andExpect(jsonPath("$[2].status").value("NOT_APPROVED"));

        // Verify
        verify(absenceRepository).findAllByEmployeeId(employeeId);
    }

    @Test
    void getAbsenceByEmployeeId_ShouldReturnEmptyArray_WhenEmployeeHasNoAbsences() throws Exception {
        // Arrange
        given(absenceRepository.findAllByEmployeeId(employeeId)).willReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/absence/employee/{employeeId}", employeeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        // Verify
        verify(absenceRepository).findAllByEmployeeId(employeeId);
    }

    @Test
    void getAbsenceByEmployeeId_ShouldReturnEmptyArray_WhenEmployeeDoesNotExist() throws Exception {
        // Arrange
        UUID nonExistentEmployeeId = UUID.fromString("333e4567-e89b-12d3-a456-426614174000");
        given(absenceRepository.findAllByEmployeeId(nonExistentEmployeeId)).willReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/absence/employee/{employeeId}", nonExistentEmployeeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));

        // Verify
        verify(absenceRepository).findAllByEmployeeId(nonExistentEmployeeId);
    }

    @Test
    void getAbsenceByEmployeeId_ShouldReturnAbsencesWithDifferentStatuses() throws Exception {
        // Arrange
        List<Absence> mixedStatusAbsences = Arrays.asList(
                new Absence(
                        UUID.fromString("523e4567-e89b-12d3-a456-426614174000"),
                        AbsenceType.DAY_OFF,
                        AbsenceStatus.APPROVED,
                        LocalDate.of(2024, 4, 1),
                        LocalDate.of(2024, 4, 1),
                        "Personal",
                        true,
                        employeeId
                ),
                new Absence(
                        UUID.fromString("623e4567-e89b-12d3-a456-426614174000"),
                        AbsenceType.VACATION,
                        AbsenceStatus.NOT_APPROVED,
                        LocalDate.of(2024, 5, 1),
                        LocalDate.of(2024, 5, 10),
                        "Beach",
                        false,
                        employeeId
                )
        );

        given(absenceRepository.findAllByEmployeeId(employeeId)).willReturn(mixedStatusAbsences);

        // Act & Assert
        mockMvc.perform(get("/api/absence/employee/{employeeId}", employeeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].type").value("DAY_OFF"))
                .andExpect(jsonPath("$[0].status").value("APPROVED"))
                .andExpect(jsonPath("$[1].type").value("VACATION"))
                .andExpect(jsonPath("$[1].status").value("NOT_APPROVED"));

        // Verify
        verify(absenceRepository).findAllByEmployeeId(employeeId);
    }
}