package com.petproject.workflow.config;

import com.petproject.workflow.store.User;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class UserServiceClient implements UserDetailsService {

    private static final String BASE_URL = "http://localhost:9100/api/users";

    private RestTemplate restTemplate;
    public UserServiceClient() {
//        // Настройка OAuth2 клиента для client_credentials flow
//        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
//        resourceDetails.setClientId("auth-server-client");  // ID клиента в auth-server
//        resourceDetails.setClientSecret("auth-server-secret");
//        resourceDetails.setAccessTokenUri("http://localhost:8080/oauth/token");
//        resourceDetails.setScope(List.of("internal"));  // scope для доступа к user-service
//
//        this.restTemplate = new OAuth2RestTemplate(resourceDetails);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        String url = BASE_URL + "/" + username;
//
//        try {
//            // Запрос к user-service с автоматическим добавлением OAuth2 токена
//            ResponseEntity<User> response = restTemplate.exchange(
//                    url,
//                    HttpMethod.GET,
//                    null,
//                    User.class
//            );
//
//            UserDto userDto = response.getBody();
//            if (userDto == null) {
//                throw new UsernameNotFoundException("User not found: " + username);
//            }
//
//            // Преобразование UserDto в UserDetails
//            return User.builder()
//                    .username(userDto.getUsername())
//                    .password(userDto.getPassword())
//                    .roles(userDto.getRoles().toArray(new String[0]))
//                    .build();
//
//        } catch (HttpClientErrorException.NotFound e) {
//            throw new UsernameNotFoundException("User not found: " + username);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to fetch user data", e);
//        }
        return  restTemplate.getForObject(BASE_URL + "/" + username, User.class);
    }
}
