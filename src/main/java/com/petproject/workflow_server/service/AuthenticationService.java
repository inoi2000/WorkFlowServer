package com.petproject.workflow_server.service;

import com.petproject.workflow_server.dtos.AuthenticationResponse;
import com.petproject.workflow_server.dtos.SignInRequest;
import com.petproject.workflow_server.dtos.SignUpRequest;
import com.petproject.workflow_server.entities.Role;
import com.petproject.workflow_server.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse signUp(SignUpRequest request) {

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        user = userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new AuthenticationResponse(user.getId(), jwt);
    }

    public AuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = (User) userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new AuthenticationResponse(user.getId(), jwt);
    }
}
