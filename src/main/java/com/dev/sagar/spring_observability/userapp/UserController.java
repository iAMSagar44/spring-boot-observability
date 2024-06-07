package com.dev.sagar.spring_observability.userapp;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final Counter retrieveUsersCounter;
    private final Counter retrieveUserByIdCounter;

    public UserController(UserService userService, MeterRegistry meterRegistry) {
        this.userService = userService;
        this.retrieveUsersCounter = Counter.builder("user.list.requests")
                .description("Number of times users are retrieved")
                .register(meterRegistry);
        this.retrieveUserByIdCounter = Counter.builder("user.id.requests")
                .description("Number of times a user is retrieved by id")
                .register(meterRegistry);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        LOGGER.info("Fetching users from the API");
        retrieveUsersCounter.increment();
        return userService.getUsers();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable  int id) {
        LOGGER.info("Fetching user with id: {}", id);
        retrieveUserByIdCounter.increment();
        return userService.getUser(id);
    }
}
