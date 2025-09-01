package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.EmployeeDto;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
public class EmployeeServiceClient {

    private static final String BASE_URL = "http://localhost:9300/api/employees";

    private final RestTemplate restTemplate;
    private final AuthorizedClientServiceOAuth2AuthorizedClientManager clientManager;

    public EmployeeServiceClient(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService
    ) {
        this.clientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository,
                authorizedClientService
        );

        this.restTemplate = new RestTemplate();
    }

    public Iterable<EmployeeDto> getEmployeesByIds(Set<UUID> uuids) {
        HttpHeaders headers = getHeaders();
        ResponseEntity<EmployeeDto[]> response = restTemplate.exchange(
                BASE_URL + "/batch",
                HttpMethod.POST,
                new HttpEntity<>(uuids, headers),
                EmployeeDto[].class
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            EmployeeDto[] employees = response.getBody();
            if (employees != null) {
                return Arrays.asList(employees);
            }
        }
        return new ArrayList<>();
    }

    public EmployeeDto getEmployeeById(UUID id) {
        HttpHeaders headers = getHeaders();
        ResponseEntity<EmployeeDto> response = restTemplate.exchange(
                BASE_URL + "/" + id.toString(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                EmployeeDto.class
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    private HttpHeaders getHeaders() {
        // Получаем OAuth2 токен
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("task-service-client") // ID клиента из application.properties
                .principal("internal-service") // Условное имя "сервисного" пользователя
                .build();

        OAuth2AuthorizedClient authorizedClient = clientManager.authorize(authorizeRequest);
        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        // Делаем запрос с токеном
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        return headers;
    }
}
