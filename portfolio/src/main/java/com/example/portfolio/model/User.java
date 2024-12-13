package com.example.portfolio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
    private String id;
    private String name;
    private String email;
    @JsonIgnore
    private Portfolio portfolio; // Reference to the user's portfolio



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
