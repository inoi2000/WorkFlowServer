package com.petproject.workflow.api;

import com.petproject.workflow.store.Absence;
import com.petproject.workflow.store.AbsenceRepository;
import com.petproject.workflow.store.AbsenceStatus;
import com.petproject.workflow.store.AbsenceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AbsenceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AbsenceRepository absenceRepository;

    private UUID testId;
    private UUID employeeId;
    private UUID nonExistentId;

    @BeforeEach
    void setUp() {
        // Очищаем базу перед каждым тестом
        absenceRepository.deleteAll();

        testId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        employeeId = UUID.fromString("223e4567-e89b-12d3-a456-426614174000");
        nonExistentId = UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff");

        // Сохраняем тестовые данные в реальную БД
        Absence absence1 = new Absence(
                testId,
                AbsenceType.VACATION,
                AbsenceStatus.ON_APPROVAL,
                LocalDate.of(2024, 1, 15),
                LocalDate.of(2024, 1, 20),
                "Home Office",
                true,
                employeeId
        );

        Absence absence2 = new Absence(
                UUID.fromString("323e4567-e89b-12d3-a456-426614174000"),
                AbsenceType.SICK_LEAVE,
                AbsenceStatus.APPROVED,
                LocalDate.of(2024, 2, 1),
                LocalDate.of(2024, 2, 3),
                "Home",
                false,
                employeeId
        );

        Absence absence3 = new Absence(
                UUID.fromString("423e4567-e89b-12d3-a456-426614174000"),
                AbsenceType.BUSINESS_TRIP,
                AbsenceStatus.NOT_APPROVED,
                LocalDate.of(2024, 3, 10),
                LocalDate.of(2024, 3, 15),
                "Client office",
                true,
                employeeId
        );

        absenceRepository.save(absence1);
        absenceRepository.save(absence2);
        absenceRepository.save(absence3);
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"HR"})
    void getAbsenceById_ShouldReturnAbsence_WhenExists() throws Exception {
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
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"HR"})
    void getAbsenceById_ShouldReturnNotFound_WhenDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/absence/{id}", nonExistentId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"HR"})
    void getAbsenceByEmployeeId_ShouldReturnAbsences_WhenEmployeeHasAbsences() throws Exception {
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
    }

    @Test
    @WithMockUser(username = "test-user", roles = {"HR"})
    void getAbsenceByEmployeeId_ShouldReturnEmptyArray_WhenEmployeeHasNoAbsences() throws Exception {
        UUID employeeWithoutAbsences = UUID.fromString("333e4567-e89b-12d3-a456-426614174000");

        mockMvc.perform(get("/api/absence/employee/{employeeId}", employeeWithoutAbsences))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
