package com.petproject.workflow.config;

import com.petproject.workflow.services.RestUserService;
import com.petproject.workflow.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests( request -> request
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/oauth2/authorization/admin-client"))
                .oauth2Client(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @RequestScope
    public UserService userService(
            OAuth2AuthorizedClientService clientService) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String accessToken = null;

        if (authentication.getClass()
                .isAssignableFrom(OAuth2AuthenticationToken.class)) {
            OAuth2AuthenticationToken oauthToken =
                    (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId =
                    oauthToken.getAuthorizedClientRegistrationId();
            if (clientRegistrationId.equals("admin-client")) {
                OAuth2AuthorizedClient client =
                        clientService.loadAuthorizedClient(
                                clientRegistrationId, oauthToken.getName());
                accessToken = client.getAccessToken().getTokenValue();
            }
        }
        return new RestUserService(accessToken);
    }
}
