package com.petproject.workflow.config;

import com.petproject.workflow.store.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceClient implements UserDetailsService {

    @Value("${workflow.user-service.base-uri}")
    private String baseUrl;

    private final RestTemplate restTemplate;
    private final AuthorizedClientServiceOAuth2AuthorizedClientManager clientManager;

    public UserServiceClient(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService
    ) {
        this.clientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository,
                authorizedClientService
        );

        this.restTemplate = new RestTemplate();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Получаем OAuth2 токен
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("auth-server-client") // ID клиента из application.yml
                .principal("internal-service") // Условное имя "сервисного" пользователя
                .build();

        OAuth2AuthorizedClient authorizedClient = clientManager.authorize(authorizeRequest);
        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        // Делаем запрос с токеном
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        ResponseEntity<User> response = restTemplate.exchange(
                baseUrl + username,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                User.class
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else  {
            throw new UsernameNotFoundException("<UNK>");
        }
    }
}
