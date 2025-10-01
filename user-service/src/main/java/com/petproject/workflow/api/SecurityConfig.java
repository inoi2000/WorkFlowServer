package com.petproject.workflow.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        return http
                .authorizeExchange(request -> request
                        .pathMatchers("/api/users/auth/**").hasAuthority("INTERNAL")
                        .pathMatchers("/api/users", "/api/users/**").hasAuthority("ROLE_ADMIN")
                        .pathMatchers("/", "/**").permitAll())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(reactiveJwtAuthenticationConverter())
                        ))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveJwtAuthenticationConverter reactiveJwtAuthenticationConverter() {
        var convertor = new ReactiveJwtAuthenticationConverter();
        convertor.setJwtGrantedAuthoritiesConverter(jwt -> Flux
                .just(
                        Optional.ofNullable(jwt.<List<String>>getClaim("authorities"))
                                .orElse(Collections.emptyList()),
                        Optional.ofNullable(jwt.<List<String>>getClaim("scope")).
                                orElse(Collections.emptyList()))
                .flatMap(Flux::fromIterable)
                .filter(Objects::nonNull)
                .filter(Predicate.not(String::isBlank))
                .map(SimpleGrantedAuthority::new)
        );
        return convertor;
    }
}
