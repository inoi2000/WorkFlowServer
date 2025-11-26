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
            AccessRepository accessRepository,
            InstructionDataRepository instructionDataRepository,
            InstructionRepository instructionRepository,
            InstructionConfirmationRepository instructionConfirmationRepository) {
        return args -> {

            instructionConfirmationRepository.deleteAll();
            instructionRepository.deleteAll();
            instructionDataRepository.deleteAll();
            accessRepository.deleteAll();

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
                    UUID.fromString("23347472-98be-4d11-9c0c-66bcfaca14cc"),
                    "Системный администратор",
                    1000,
                    false
            );
            Position directorPosition = new Position(
                    UUID.fromString("78476c70-d901-4075-9aae-4416c06a3fa4"),
                    "Директор",
                    900,
                    true
            );
            Position hrPosition = new Position(
                    UUID.fromString("dc81fee5-afa1-4379-80b2-9ee26a7a74d4"),
                    "Кадровик",
                    700,
                    false
            );
            Position industrialSecurityPosition = new Position(
                    UUID.fromString("9d5df9ff-4f44-4f74-aff6-a0c3c5711581"),
                    "Пром-безопасность",
                    700,
                    false
            );
            Position driverPosition = new Position(
                    UUID.fromString("355d30fa-68e3-442c-be6a-33b9c2483300"),
                    "Водитель",
                    500,
                    true
            );
            Position logistPosition = new Position(
                    UUID.fromString("62862eb3-e692-4d75-b15c-f348ec95c62c"),
                    "Логист",
                    600,
                    false
            );
            Position operatorPosition = new Position(
                    UUID.fromString("d4063c51-251e-4e80-836d-20ddeb921b28"),
                    "Оператор",
                    550,
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
                    "123e4567-e89b-12d3-a456-426614174000.png",
                    "+79033653774",
                    "mishavinov@mail.ru",
                    adminPosition,
                    null
            );
            Employee director = new Employee(UUID.fromString(
                    "f81d4fae-7dec-11d0-a765-00a0c91e6bf6"),
                    "Иванов Иван Иванович",
                    "f81d4fae-7dec-11d0-a765-00a0c91e6bf6.png",
                    "+79066653994",
                    "ivanov@inbox.ru",
                    directorPosition,
                    office
            );
            Employee hr = new Employee(UUID.fromString(
                    "16763be4-6022-406e-a950-fcd5018633ca"),
                    "Яковлева Елена Сергеевна",
                    "16763be4-6022-406e-a950-fcd5018633ca.png",
                    "+79256653999",
                    "yacovleva@mail.ru",
                    hrPosition,
                    office
            );
            Employee industrialSecurity = new Employee(UUID.fromString(
                    "73ea403e-8c9a-4cf8-bc7a-88d68dfcc20f"),
                    "Сидоров Виталий Иванович",
                    "73ea403e-8c9a-4cf8-bc7a-88d68dfcc20f.png",
                    "+79228494122",
                    "sidorov@mail.ru",
                    industrialSecurityPosition,
                    base
            );
            Employee driver = new Employee(UUID.fromString(
                    "1a6fce5a-cd67-11eb-b8bc-0242ac130003"),
                    "Стариханов Федр Петрович",
                    "1a6fce5a-cd67-11eb-b8bc-0242ac130003.png",
                    "+79067853343",
                    "staricov22@mail.ru",
                    driverPosition,
                    base
            );
            Employee logist = new Employee(UUID.fromString(
                    "96690d40-dfb1-473c-a1ef-e6abb05061ca"),
                    "Семакина Татьяна Юрьевна",
                    "96690d40-dfb1-473c-a1ef-e6abb05061ca.jpg",
                    "+79067850909",
                    "semakina@mail.ru",
                    logistPosition,
                    office
            );
            Employee operator = new Employee(UUID.fromString(
                    "8d41cbf0-f0e5-4b62-b8b5-419381457931"),
                    "Савельев Петр Петрович",
                    "8d41cbf0-f0e5-4b62-b8b5-419381457931.jpg",
                    "+79067858888",
                    "saveliev@mail.ru",
                    operatorPosition,
                    base
            );

            employeeRepository.save(admin);
            employeeRepository.save(director);
            employeeRepository.save(hr);
            employeeRepository.save(industrialSecurity);
            employeeRepository.save(driver);
            employeeRepository.save(logist);
            employeeRepository.save(operator);

            InstructionData instructionData = new InstructionData(
                    UUID.fromString("4e259e04-8f27-4254-bbd6-3962c1d86106"),
                    "Ежедневный инструктаж по пожарной безопасности",
                    "Работники обязаны соблюдать инструктаж по противопожарной безопасности, быть бдительными"
            );
            instructionDataRepository.save(instructionData);
            Instruction instruction = new Instruction(
                    UUID.fromString("bc66e0af-0f63-4110-89ad-1d49da666a33"),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    LocalDate.now().plusDays(7),
                    UUID.fromString("73ea403e-8c9a-4cf8-bc7a-88d68dfcc20f"),
                    instructionData,
                    null
            );
            instructionRepository.save(instruction);
            InstructionConfirmation instructionConfirmation = new InstructionConfirmation(
                    new InstructionConfirmationKey(
                            UUID.fromString("1a6fce5a-cd67-11eb-b8bc-0242ac130003"),
                            UUID.fromString("bc66e0af-0f63-4110-89ad-1d49da666a33")
                    ),
                    true,
                    LocalDateTime.now()
            );
            instructionConfirmationRepository.save(instructionConfirmation);

            AccessData accessData = new AccessData(
                    UUID.fromString("4e259e04-8f27-4254-bbd6-3962c1d86106"),
                    "Допуск к работе на высоте",
                    "Разрешение на проведение работ на высоте более 1.8 метра"
            );

            Access access = new Access(
                    UUID.randomUUID(),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    AccessDurationType.PERMANENT,
                    LocalDate.now().plusDays(10),
                    accessData,
                    UUID.fromString("73ea403e-8c9a-4cf8-bc7a-88d68dfcc20f"),
                    UUID.fromString("1a6fce5a-cd67-11eb-b8bc-0242ac130003")
            );
            accessRepository.save(access);
        };
    }
}
