package com.petproject.workflow.controllers;

import com.petproject.workflow.dtos.RegistrationForm;
import com.petproject.workflow.dtos.User;
import com.petproject.workflow.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/admin/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @GetMapping
    public String registerForm(Model model) {
        Iterable<String> roles = userService.getRoles();
//        model.addAttribute("roles", roles);
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {
        UUID uuid = UUID.randomUUID();
        User user = form.toUser(uuid, passwordEncoder);
        userService.addUser(user);
        return "redirect:/";
    }
}
