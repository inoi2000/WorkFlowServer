package com.petproject.workflow.api;

import com.petproject.workflow.store.Role;
import com.petproject.workflow.store.User;
import com.petproject.workflow.store.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/users", produces="application/json")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/auth/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user) {
        user.setId(UUID.randomUUID());
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED) ;
    }

    @GetMapping("/roles")
    public String[] getRoles() {
        return Arrays.stream(Role.values()).map(Enum::name).toArray(String[]::new);
    }
}