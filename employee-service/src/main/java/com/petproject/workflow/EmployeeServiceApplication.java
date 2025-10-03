package com.petproject.workflow;

import com.petproject.workflow.store.Employee;
import com.petproject.workflow.store.EmployeeRepository;
import com.petproject.workflow.store.Position;
import com.petproject.workflow.store.PositionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class EmployeeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(
            EmployeeRepository employeeRepository,
            PositionRepository positionRepository)
    {
        return args -> {
            employeeRepository.deleteAll();
            positionRepository.deleteAll();

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
            Position driverPosition = new Position(
                    UUID.randomUUID(),
                    "Водитель",
                    500,
                    true
            );

            positionRepository.save(adminPosition);
            positionRepository.save(directorPosition);
            positionRepository.save(hrPosition);
            positionRepository.save(driverPosition);

            Employee admin = new Employee(UUID.fromString(
                    "123e4567-e89b-12d3-a456-426614174000"),
                    "Авинов Михаил Сергеевич",
                    "+79033653774",
                    adminPosition
            );
            Employee director = new Employee(UUID.fromString(
                    "f81d4fae-7dec-11d0-a765-00a0c91e6bf6"),
                    "Иванов Иван Иванович",
                    "+79066653994",
                    directorPosition
            );
            Employee hr = new Employee(UUID.fromString(
                    "16763be4-6022-406e-a950-fcd5018633ca"),
                    "Яковлева Елена Сергеевна",
                    "+79256653999",
                    hrPosition
            );
            Employee driver = new Employee(UUID.fromString(
                    "1a6fce5a-cd67-11eb-b8bc-0242ac130003"),
                    "Стариханов Федр Петрович",
                    "+79067853343",
                    driverPosition
            );

            employeeRepository.save(admin);
            employeeRepository.save(director);
            employeeRepository.save(hr);
            employeeRepository.save(driver);
        };
    }
}
