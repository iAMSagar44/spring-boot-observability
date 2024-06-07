package com.dev.sagar.spring_observability.userapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final RestClient restClient;

    public UserService(RestClient.Builder restClient) {
        this.restClient = restClient.baseUrl("https://jsonplaceholder.typicode.com")
                .build();
    }

    List<User> getUsers() {
        return restClient.get()
                .uri("/users")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    User getUser(int id) {
        LOGGER.info("Fetching user with id: {}", id);
        return restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .body(User.class);
    }
}
