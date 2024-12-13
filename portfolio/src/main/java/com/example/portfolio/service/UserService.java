package com.example.portfolio.service;

import com.example.portfolio.model.Portfolio;
import com.example.portfolio.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private static UserService userService;
    private Map<String, User> users;

    private UserService() {
        this.users = new HashMap<>();
    }

    public static UserService getUserService(){
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }


    public void addUser(User user){
        if(users.containsKey(user.getId())){
            throw new IllegalArgumentException("User Already Exists");
        }
        // Create a new portfolio for the user
        user.setPortfolio(new Portfolio(user));
        users.put(user.getId(),user);
    }


    public void removeUser(String userID){
        if (!users.containsKey(userID)) {
            throw new IllegalArgumentException("User not found.");
        }
        users.remove(userID);
    }

    // Method to get a user by ID
    public User getUserById(String userId) {
        return users.get(userId);
    }

    // Method to get all users
    public Map<String, User> getAllUsers() {
        return users;
    }
}
