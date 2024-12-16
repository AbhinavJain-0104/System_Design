package com.example.portfoliomanagement.controller;

import com.example.portfoliomanagement.exception.UserAlreadyExistsException;
import com.example.portfoliomanagement.exception.UserNotFoundException;
import com.example.portfoliomanagement.model.User;
import com.example.portfoliomanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create a new user
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) throws UserAlreadyExistsException {
        logger.info("Received request to create user: {}", user.getUserName());
        userService.createUser(user);
        return ResponseEntity.ok("User created successfully with ID: " + user.getUserId());
    }

    // Update an existing user
    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody User updatedUser) throws UserNotFoundException {
        logger.info("Received request to update user with ID: {}", userId);
        userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok("User updated successfully.");
    }

    // Get details of a single user
    @GetMapping("/view/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) throws UserNotFoundException {
        logger.info("Received request to fetch user with ID: {}", userId);
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    // Get all users
    @GetMapping("/view/all")
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Received request to fetch all users.");
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
