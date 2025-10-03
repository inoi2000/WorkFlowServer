package com.petproject.workflow.store;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User {

    @Id
    @Column("id")
    private UUID id;

    @NotNull
    @Size(min = 5, message = "Username must be at least 5 characters long")
    @Column("username")
    private String username;

    @NotNull
    @Size(min = 5, message = "Password must be at least 5 characters long")
    @Column("password")
    private String password;

    @NotNull
    @Column("email")
    private String email;

    @NotNull
    @Column("role")
    private Role role;
}