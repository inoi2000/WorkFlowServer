package com.petproject.workflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

@Configuration
public class OAuth2ClientConfig {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            RegisteredClientRepository registeredClientRepository) {

        RegisteredClient registeredClient = registeredClientRepository.findByClientId("auth-server-client");

        ClientRegistration clientRegistration = ClientRegistration
                .withRegistrationId(registeredClient.getClientId())
                .clientId(registeredClient.getClientId())
                .clientSecret("secret")
                .authorizationGrantType(registeredClient.getAuthorizationGrantTypes().iterator().next())
                .scope(registeredClient.getScopes())
//                .tokenUri("http://localhost:9000/oauth2/token")
                .tokenUri("https://192.168.0.159:9000/oauth2/token")
                .build();

        return new InMemoryClientRegistrationRepository(clientRegistration);
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(
            ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }
}
