package com.example.portfoliomanagement.service;

import com.example.portfoliomanagement.exception.UserAlreadyExistsException;
import com.example.portfoliomanagement.exception.UserNotFoundException;
import com.example.portfoliomanagement.model.User;

import java.util.List;

public interface UserService {
    void createUser(User user) throws UserAlreadyExistsException; // Add a new user
    void updateUser(String userId, User updatedUser) throws UserNotFoundException; // Update existing user details
    User getUserById(String userId) throws UserNotFoundException; // Fetch user details (including portfolio)
    List<User> getAllUsers(); // Fetch all users
    void deleteUser(String userId) throws UserNotFoundException; // Delete a user and their portfolio
}
