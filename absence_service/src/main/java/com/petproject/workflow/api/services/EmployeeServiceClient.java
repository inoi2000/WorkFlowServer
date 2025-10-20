package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.EmployeeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

@Service
public class EmployeeServiceClient {

    @Value("${workflow.employee-service.base-uri}")
    private String baseUrl;

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
                baseUrl + "/batch",
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
                baseUrl + "/" + id.toString(),
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
                .withClientRegistrationId("absence-service-client") // ID клиента из application.properties
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
