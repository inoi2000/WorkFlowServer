package com.petproject.workflow.config;

import com.petproject.workflow.store.User;
import com.petproject.workflow.store.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Order(2)
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/connect/logout").permitAll())
                .csrf(csrf -> csrf.ignoringRequestMatchers("/login", "/connect/logout"))
        ;
        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            Optional<User> optionalUser = userRepository.findByUsername(username);
            return optionalUser.orElseThrow(() -> new UsernameNotFoundException("<UNK>"));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        var bcrypt = new BCryptPasswordEncoder();
        System.out.printf("bcrypt:%s\n", bcrypt.encode("secret"));
        return bcrypt;
    }
}
