package com.petproject.workflow;

import com.petproject.workflow.store.Role;
import com.petproject.workflow.store.User;
import com.petproject.workflow.store.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Flux;

import java.util.UUID;

@SpringBootApplication
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(
            UserRepository userRepository,
            PasswordEncoder encoder,
            R2dbcEntityTemplate entityTemplate) {
        return args -> {
            userRepository.deleteAll()
                    .thenMany(Flux.just(
                            createUser("123e4567-e89b-12d3-a456-426614174000", "mishavinov", "12345", Role.ROLE_ADMIN, encoder),
                            createUser("f81d4fae-7dec-11d0-a765-00a0c91e6bf6", "ivanov", "12345", Role.ROLE_DIRECTOR, encoder),
                            createUser("16763be4-6022-406e-a950-fcd5018633ca", "yakovleva09", "12345", Role.ROLE_HR, encoder),
                            createUser("73ea403e-8c9a-4cf8-bc7a-88d68dfcc20f", "sidorov", "12345", Role.ROLE_INDUSTRIAL_SECURITY, encoder),
                            createUser("1a6fce5a-cd67-11eb-b8bc-0242ac130003", "starii", "12345", Role.ROLE_DRIVER, encoder)
                    ))
                    .flatMap(user -> entityTemplate.insert(User.class).using(user))
                    .subscribe(
                            user -> System.out.println("Saved user: " + user.getUsername()),
                            error -> System.err.println("Error: " + error),
                            () -> System.out.println("Data loading completed")
                    );
        };
    }

    private User createUser(String uuid, String username, String password, Role role, PasswordEncoder encoder) {
        return User.builder()
                .id(UUID.fromString(uuid))
                .username(username)
                .password(encoder.encode(password))
                .role(role)
                .build();
    }
}
