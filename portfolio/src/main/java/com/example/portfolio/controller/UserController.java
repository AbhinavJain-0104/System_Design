package com.example.portfolio.controller;

import com.example.portfolio.model.User;
import com.example.portfolio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService; // Initialize the user service
    }

    // Method to add a new user
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>("User added successfully.", HttpStatus.CREATED);
    }

    // Method to remove a user
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> removeUser(@PathVariable String userId) {
        userService.removeUser(userId);
        return new ResponseEntity<>("User removed successfully.", HttpStatus.OK);
    }

    // Method to get a user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Method to get all users
    @GetMapping("/all")
    public ResponseEntity<Map<String, User>> getAllUsers() {
        Map<String, User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
}