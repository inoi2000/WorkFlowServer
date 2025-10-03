package com.petproject.workflow.store;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, UUID>
{
    @Query("""
        INSERT INTO users (id, username, email, password, role)
        VALUES (:#{#user.id}, :#{#user.username}, :#{#user.email}, :#{#user.password}, :#{#user.role});
        SELECT * FROM users WHERE id = :#{#user.id}
        """)
    Mono<User> insert(@Param("user") User user);

    Mono<User> findByUsername(String username);

    Mono<Boolean> existsUserByUsername(String username);

    Mono<Boolean> existsUserByEmail(String email);
}