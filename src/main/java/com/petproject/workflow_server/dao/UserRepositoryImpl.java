package com.petproject.workflow_server.dao;

import com.petproject.workflow_server.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Query query = entityManager.createQuery("SELECT user FROM User user WHERE user.username = :username");
        query.setParameter("username", username);
        Object result = query.getSingleResult();
        return Optional.of((User) result);
    }

    @Override
    public boolean existsByUsername(String username) {
        Query query = entityManager.createQuery("SELECT user FROM User user WHERE user.username = :username");
        query.setParameter("username", username);
        query.getFirstResult();
        return query.getFirstResult() != 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        Query query = entityManager.createQuery("SELECT user FROM User user WHERE user.email = :email");
        query.setParameter("email", email);
        query.getFirstResult();
        return query.getFirstResult() != 0;
    }
}
