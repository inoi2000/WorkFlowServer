package com.petproject.workflow.services;

import com.petproject.workflow.dtos.User;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class RestUserService implements UserService {

    private static final String BASE_URL = "http://localhost:9100/api/users";

    private RestTemplate restTemplate;

    public RestUserService(String accessToken) {
        this.restTemplate = new RestTemplate();
        if (accessToken != null) {
            this.restTemplate
                    .getInterceptors()
                    .add(getBearerTokenInterceptor(accessToken));
        }
    }

    @Override
    public User addUser(User user) {
        return restTemplate.postForObject(
                BASE_URL,
                user,
                User.class);
    }

    @Override
    public Iterable<User> findAll() {
        return Arrays.asList(restTemplate.getForObject(
                BASE_URL,
                User[].class));
    }

    private ClientHttpRequestInterceptor getBearerTokenInterceptor(String accessToken) {
        return (request, bytes, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + accessToken);
            return execution.execute(request, bytes);
        };
    }
}