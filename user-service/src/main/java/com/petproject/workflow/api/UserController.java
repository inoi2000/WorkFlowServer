package com.petproject.workflow.api;

import com.petproject.workflow.store.Role;
import com.petproject.workflow.store.User;
import com.petproject.workflow.store.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/users", produces = "application/json")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/auth/{username}")
    public Mono<ResponseEntity<User>> getUser(@PathVariable("username") String username) {
        return userRepository.findByUsername(username)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json")
    public Mono<ResponseEntity<User>> insertUser(@RequestBody @Valid User user) {
        return Mono.just(user)
                .filterWhen(obj -> userRepository.existsUserByUsername(obj.getUsername()))
                .flatMap(obj -> {
                    obj.setId(UUID.randomUUID());
                    return userRepository.insert(obj);
                })
                .map(obj -> new ResponseEntity<>(obj, HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/roles")
    public String[] getRoles() {
        return Arrays.stream(Role.values()).map(Enum::name).toArray(String[]::new);
    }
}