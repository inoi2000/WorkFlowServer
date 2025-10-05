package com.petproject.workflow;

import com.petproject.workflow.store.entities.*;
import com.petproject.workflow.store.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
public class EmployeeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(
            EmployeeRepository employeeRepository,
            PositionRepository positionRepository,
            DepartmentRepository departmentRepository,
            InstructionDataRepository instructionDataRepository,
            InstructionRepository instructionRepository,
            EmployeeInstructionStatusRepository employeeInstructionStatusRepository)
    {
        return args -> {

            employeeInstructionStatusRepository.deleteAll();
            instructionRepository.deleteAll();
            instructionDataRepository.deleteAll();

            employeeRepository.deleteAll();
            positionRepository.deleteAll();
            departmentRepository.deleteAll();

            Department office = new Department(
                    UUID.randomUUID(),
                    "Офис"
            );
            Department base = new Department(
                    UUID.randomUUID(),
                    "База"
            );
            departmentRepository.save(office);
            departmentRepository.save(base);

            Position adminPosition = new Position(
                    UUID.randomUUID(),
                    "Системный администратор",
                    1000,
                    false
            );
            Position directorPosition = new Position(
                    UUID.randomUUID(),
                    "Директор",
                    900,
                    true
            );
            Position hrPosition = new Position(
                    UUID.randomUUID(),
                    "Кадровик",
                    700,
                    false
            );
            Position industrialSecurityPosition = new Position(
                    UUID.randomUUID(),
                    "Пром-безопасность",
                    700,
                    false
            );
            Position driverPosition = new Position(
                    UUID.randomUUID(),
                    "Водитель",
                    500,
                    true
            );

            positionRepository.save(adminPosition);
            positionRepository.save(directorPosition);
            positionRepository.save(hrPosition);
            positionRepository.save(industrialSecurityPosition);
            positionRepository.save(driverPosition);

            Employee admin = new Employee(UUID.fromString(
                    "123e4567-e89b-12d3-a456-426614174000"),
                    "Авинов Михаил Сергеевич",
                    "+79033653774",
                    "mishavinov@mail.ru",
                    adminPosition,
                    null
            );
            Employee director = new Employee(UUID.fromString(
                    "f81d4fae-7dec-11d0-a765-00a0c91e6bf6"),
                    "Иванов Иван Иванович",
                    "+79066653994",
                    "ivanov@inbox.ru",
                    directorPosition,
                    office
            );
            Employee hr = new Employee(UUID.fromString(
                    "16763be4-6022-406e-a950-fcd5018633ca"),
                    "Яковлева Елена Сергеевна",
                    "+79256653999",
                    "yacovleva@mail.ru",
                    hrPosition,
                    office
            );
            Employee industrialSecurity = new Employee(UUID.fromString(
                    "73ea403e-8c9a-4cf8-bc7a-88d68dfcc20f"),
                    "Сидоров Виталий Иванович",
                    "+79228494122",
                    "sidorov@mail.ru",
                    industrialSecurityPosition,
                    base
            );
            Employee driver = new Employee(UUID.fromString(
                    "1a6fce5a-cd67-11eb-b8bc-0242ac130003"),
                    "Стариханов Федр Петрович",
                    "+79067853343",
                    "staricov22@mail.ru",
                    driverPosition,
                    base
            );

            employeeRepository.save(admin);
            employeeRepository.save(director);
            employeeRepository.save(hr);
            employeeRepository.save(industrialSecurity);
            employeeRepository.save(driver);

            InstructionData instructionData = new InstructionData(
                    UUID.fromString("4e259e04-8f27-4254-bbd6-3962c1d86106"),
                    "Ежедневный инструктаж по пожарной безопасности"
            );
            instructionDataRepository.save(instructionData);
            Instruction instruction = new Instruction(
                    UUID.fromString("bc66e0af-0f63-4110-89ad-1d49da666a33"),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    LocalDate.now().plusDays(7),
                    UUID.fromString("73ea403e-8c9a-4cf8-bc7a-88d68dfcc20f"),
                    instructionData,
                    false,
                    null,
                    null
            );
            instructionRepository.save(instruction);
            EmployeeInstructionStatus employeeInstructionStatus = new EmployeeInstructionStatus(
                    new EmployeeInstructionKey(
                            UUID.fromString("1a6fce5a-cd67-11eb-b8bc-0242ac130003"),
                            UUID.fromString("bc66e0af-0f63-4110-89ad-1d49da666a33")
                    ),
                    false,
                    LocalDateTime.now()
            );
            employeeInstructionStatusRepository.save(employeeInstructionStatus);

//            System.out.println(instructionRepository.findAllByEmployeeId(UUID.fromString("1a6fce5a-cd67-11eb-b8bc-0242ac130003")));
//            System.out.println(instructionRepository.findAllByEmployeeId(UUID.fromString("bc66e0af-0f63-4110-89ad-1d49da666a33")));

        };
    }
}
