package com.petproject.workflow_server.store.repositories;

import com.petproject.workflow_server.store.entities.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}