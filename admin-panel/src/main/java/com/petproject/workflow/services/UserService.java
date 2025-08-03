package com.petproject.workflow.services;

import com.petproject.workflow.dtos.User;

public interface UserService {

    Iterable<User> findAll();

    User addUser(User user);

    Iterable<String> getRoles();
}
