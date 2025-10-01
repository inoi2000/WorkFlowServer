package com.petproject.workflow.store;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);
}