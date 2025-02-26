package com.petproject.workflow_server.dao;

import com.petproject.workflow_server.entities.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}