package com.petproject.workflow;

import com.petproject.workflow.store.Role;
import com.petproject.workflow.store.User;
import com.petproject.workflow.store.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class WorkFlowAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkFlowAuthServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(
            UserRepository repository,
            PasswordEncoder encoder)
    {
        return args -> {
            repository.deleteAll(); // TODO: Quick hack to avoid tests from stepping on each other with constraint violations

            User admin = new User(UUID.fromString(
                    "123e4567-e89b-12d3-a456-426614174000"),
                    "mishavinov",
                    encoder.encode("12345"),
                    "mishavinov@mail.ru",
                    Role.ROLE_ADMIN
                    );
            User director = new User(UUID.fromString(
                    "f81d4fae-7dec-11d0-a765-00a0c91e6bf6"),
                    "ivanov",
                    encoder.encode("12345"),
                    "ivanov@mail.ru",
                    Role.ROLE_DIRECTOR
            );
            User hr = new User(UUID.fromString(
                    "16763be4-6022-406e-a950-fcd5018633ca"),
                    "yakovleva09",
                    encoder.encode("12345"),
                    "yakovleva09@mail.ru",
                    Role.ROLE_HR
            );
            User driver = new User(UUID.fromString(
                    "1a6fce5a-cd67-11eb-b8bc-0242ac130003"),
                    "starii",
                    encoder.encode("12345"),
                    "starii@mail.ru",
                    Role.ROLE_DRIVER
            );

            repository.save(admin);
            repository.save(director);
            repository.save(hr);
            repository.save(driver);
        };
    }
}
