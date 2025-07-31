package com.petproject.workflow.dtos;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Data
public class RegistrationForm {

    private String username;
    private String password;
    private String email;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public User toUser(UUID id, PasswordEncoder passwordEncoder) {

        return new User(
                id,
                username,
                passwordEncoder.encode(password),
                email,
                Role.ROLE_ADMIN
        );
    }
}
