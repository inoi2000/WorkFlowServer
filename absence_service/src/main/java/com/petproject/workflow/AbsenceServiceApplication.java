package com.petproject.workflow;

import com.petproject.workflow.store.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class AbsenceServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AbsenceServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(
            AbsenceRepository absenceRepository,
            PolicyRepository policyRepository,
            ApprovalRepository approvalRepository
    ) {
        return args -> {
            // Создаем политики
            Policy vacationPolicy = createPolicy(
                    UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890"),
                    AbsenceType.VACATION,
                    30,
                    true,
                    Arrays.asList(
                            UUID.fromString("78476c70-d901-4075-9aae-4416c06a3fa4"), // директор
                            UUID.fromString("dc81fee5-afa1-4379-80b2-9ee26a7a74d4")  // кадровик
                    )
            );

            Policy businessTripPolicy = createPolicy(
                    UUID.fromString("b2c3d4e5-f6a7-8901-bcde-f23456789012"),
                    AbsenceType.BUSINESS_TRIP,
                    60,
                    true,
                    Arrays.asList(
                            UUID.fromString("78476c70-d901-4075-9aae-4416c06a3fa4") // директор
                    )
            );

            Policy sickLeavePolicy = createPolicy(
                    UUID.fromString("c3d4e5f6-a7b8-9012-cdef-345678901234"),
                    AbsenceType.SICK_LEAVE,
                    365,
                    false,
                    List.of() // не требует согласования
            );

            Policy dayOffPolicy = createPolicy(
                    UUID.fromString("d4e5f6a7-b8c9-0123-def0-456789012345"),
                    AbsenceType.DAY_OFF,
                    5,
                    true,
                    Arrays.asList(
                            UUID.fromString("78476c70-d901-4075-9aae-4416c06a3fa4") // директор
                    )
            );

            // Сохраняем политики
            policyRepository.saveAll(Arrays.asList(
                    vacationPolicy, businessTripPolicy, sickLeavePolicy, dayOffPolicy
            ));

            // Создаем отпуска
            Absence vacation1 = createAbsence(
                    UUID.fromString("e5f6a7b8-c9d0-1234-ef01-567890123456"),
                    AbsenceStatus.APPROVED,
                    LocalDate.of(2026, 6, 1),
                    LocalDate.of(2026, 6, 14),
                    "Сочи",
                    UUID.fromString("96690d40-dfb1-473c-a1ef-e6abb05061ca"), // логист
                    UUID.fromString("96690d40-dfb1-473c-a1ef-e6abb05061ca"), // создал сам себя
                    vacationPolicy
            );

            Absence vacation2 = createAbsence(
                    UUID.fromString("f6a7b8c9-d0e1-2345-f012-678901234567"),
                    AbsenceStatus.ON_APPROVAL,
                    LocalDate.of(2026, 7, 15),
                    LocalDate.of(2026, 7, 29),
                    "Крым",
                    UUID.fromString("8d41cbf0-f0e5-4b62-b8b5-419381457931"), // оператор
                    UUID.fromString("8d41cbf0-f0e5-4b62-b8b5-419381457931"), // создал сам себя
                    vacationPolicy
            );

            // Создаем командировки
            Absence businessTrip1 = createAbsence(
                    UUID.fromString("a7b8c9d0-e1f2-3456-0123-789012345678"),
                    AbsenceStatus.APPROVED,
                    LocalDate.of(2026, 5, 10),
                    LocalDate.of(2026, 5, 15),
                    "Москва, офис главный",
                    UUID.fromString("f81d4fae-7dec-11d0-a765-00a0c91e6bf6"), // директор
                    UUID.fromString("f81d4fae-7dec-11d0-a765-00a0c91e6bf6"), // директор
                    businessTripPolicy
            );

            Absence businessTrip2 = createAbsence(
                    UUID.fromString("b8c9d0e1-f2a3-4567-1234-890123456789"),
                    AbsenceStatus.NOT_APPROVED,
                    LocalDate.of(2026, 8, 1),
                    LocalDate.of(2026, 8, 10),
                    "Санкт-Петербург, встреча с партнерами",
                    UUID.fromString("1a6fce5a-cd67-11eb-b8bc-0242ac130003"), // водитель
                    UUID.fromString("1a6fce5a-cd67-11eb-b8bc-0242ac130003"), // водитель
                    businessTripPolicy
            );

            // Создаем больничные
            Absence sickLeave = createAbsence(
                    UUID.fromString("c9d0e1f2-a3b4-5678-2345-901234567890"),
                    AbsenceStatus.APPROVED,
                    LocalDate.of(2026, 4, 1),
                    LocalDate.of(2026, 4, 10),
                    "Домашний режим",
                    UUID.fromString("73ea403e-8c9a-4cf8-bc7a-88d68dfcc20f"), // пром-безопасность
                    UUID.fromString("73ea403e-8c9a-4cf8-bc7a-88d68dfcc20f"), // пром-безопасность
                    sickLeavePolicy
            );

            // Создаем отгулы
            Absence dayOff = createAbsence(
                    UUID.fromString("d0e1f2a3-b4c5-6789-3456-012345678901"),
                    AbsenceStatus.ON_APPROVAL,
                    LocalDate.of(2026, 5, 20),
                    LocalDate.of(2026, 5, 20),
                    "Семейные обстоятельства",
                    UUID.fromString("16763be4-6022-406e-a950-fcd5018633ca"), // кадровик
                    UUID.fromString("16763be4-6022-406e-a950-fcd5018633ca"), // кадровик
                    dayOffPolicy
            );

            // Сохраняем отсутствия
            absenceRepository.saveAll(Arrays.asList(
                    vacation1, vacation2, businessTrip1, businessTrip2, sickLeave, dayOff
            ));

            // Создаем согласования
            // Согласование отпуска логиста (уже согласован)
            Approval approval1 = createApproval(
                    UUID.fromString("e1f2a3b4-c5d6-7890-4567-123456789012"),
                    vacation1,
                    UUID.fromString("78476c70-d901-4075-9aae-4416c06a3fa4"), // директор
                    "Отпуск согласован, хорошего отдыха!"
            );

            Approval approval2 = createApproval(
                    UUID.fromString("f2a3b4c5-d6e7-8901-5678-234567890123"),
                    vacation1,
                    UUID.fromString("dc81fee5-afa1-4379-80b2-9ee26a7a74d4"), // кадровик
                    "Отпускные документы оформлены"
            );

            // Согласование командировки директора (сам себе)
            Approval approval3 = createApproval(
                    UUID.fromString("a3b4c5d6-e7f8-9012-6789-345678901234"),
                    businessTrip1,
                    UUID.fromString("f81d4fae-7dec-11d0-a765-00a0c91e6bf6"), // директор
                    "Командировка утверждена"
            );

            // Согласование отгула кадровика (в процессе)
            Approval approval4 = createApproval(
                    UUID.fromString("b4c5d6e7-f8a9-0123-7890-456789012345"),
                    dayOff,
                    UUID.fromString("78476c70-d901-4075-9aae-4416c06a3fa4"), // директор
                    "Рассматриваю запрос на отгул"
            );

            // Отклоненная командировка водителя
            Approval approval5 = createApproval(
                    UUID.fromString("c5d6e7f8-a9b0-1234-8901-567890123456"),
                    businessTrip2,
                    UUID.fromString("78476c70-d901-4075-9aae-4416c06a3fa4"), // директор
                    "Командировка отклонена в связи с нехваткой водителей на этот период"
            );

            // Сохраняем согласования
            approvalRepository.saveAll(Arrays.asList(
                    approval1, approval2, approval3, approval4, approval5
            ));

            System.out.println("Тестовые данные успешно загружены!");
        };
    }

    private Policy createPolicy(UUID id, AbsenceType type, Integer maxDurationDays,
                                Boolean requiresApproval, List<UUID> canApprovePositionIds) {
        Policy policy = new Policy();
        policy.setId(id);
        policy.setType(type);
        policy.setMaxDurationDays(maxDurationDays);
        policy.setRequiresApproval(requiresApproval);
        policy.setCanApprovePositionIds(canApprovePositionIds);
        return policy;
    }

    private Absence createAbsence(UUID id, AbsenceStatus status, LocalDate startDate,
                                  LocalDate endDate, String place, UUID employeeId,
                                  UUID createdById, Policy policy) {
        Absence absence = new Absence();
        absence.setId(id);
        absence.setStatus(status);
        absence.setStartDate(startDate);
        absence.setEndDate(endDate);
        absence.setPlace(place);
        absence.setEmployeeId(employeeId);
        absence.setCreatedById(createdById);
        absence.setPolicy(policy);

        LocalDateTime now = LocalDateTime.now();
        absence.setCreatedAt(now);
        absence.setUpdatedAt(now);

        return absence;
    }

    private Approval createApproval(UUID id, Absence absence, UUID approverId, String description) {
        Approval approval = new Approval();
        approval.setId(id);
        approval.setAbsence(absence);
        approval.setApproverId(approverId);
        approval.setDescription(description);
        return approval;
    }
}
