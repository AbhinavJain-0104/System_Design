package com.example.portfoliomanagement.service.impl;

import com.example.portfoliomanagement.exception.UserAlreadyExistsException;
import com.example.portfoliomanagement.exception.UserNotFoundException;
import com.example.portfoliomanagement.model.User;
import com.example.portfoliomanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final List<User> users = new ArrayList<>(); // In-memory user storage

    @Override
    public void createUser(User user) throws UserAlreadyExistsException {
        logger.info("Attempting to create user with name: {}", user.getUserName());

        // Check if user already exists
        boolean userExists = users.stream().anyMatch(existingUser -> existingUser.getUserName().equalsIgnoreCase(user.getUserName()));
        if (userExists) {
            logger.error("User with name '{}' already exists!", user.getUserName());
            throw new UserAlreadyExistsException("User with name '" + user.getUserName() + "' already exists.");
        }

        users.add(user);
        logger.info("User with name '{}' created successfully.", user.getUserName());
    }

    @Override
    public void updateUser(String userId, User updatedUser) throws UserNotFoundException {
        logger.info("Attempting to update user with ID: {}", userId);

        User existingUser = getUserById(userId); // Ensure user exists
        existingUser.setUserName(updatedUser.getUserName()); // Update user details

        logger.info("User with ID '{}' updated successfully.", userId);
    }

    @Override
    public User getUserById(String userId) throws UserNotFoundException {
        logger.info("Fetching user with ID: {}", userId);

        return users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("User with ID '{}' not found.", userId);
                    return new UserNotFoundException("User not found with ID: " + userId);
                });
    }

    @Override
    public List<User> getAllUsers() {
        logger.info("Fetching all users. Total users: {}", users.size());
        return users;
    }

    @Override
    public void deleteUser(String userId) throws UserNotFoundException {
        logger.info("Attempting to delete user with ID: {}", userId);

        User user = getUserById(userId); // Ensure user exists
        users.remove(user);

        logger.info("User with ID '{}' deleted successfully.", userId);
    }
}
