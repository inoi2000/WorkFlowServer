package com.petproject.workflow_server.api.controller;

import com.petproject.workflow_server.api.dtos.AuthenticationResponse;
import com.petproject.workflow_server.api.dtos.SignInRequest;
import com.petproject.workflow_server.api.dtos.SignUpRequest;
import com.petproject.workflow_server.api.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public AuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public AuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}