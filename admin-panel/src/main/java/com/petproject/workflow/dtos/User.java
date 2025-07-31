package com.petproject.workflow.dtos;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private Role role;
}
