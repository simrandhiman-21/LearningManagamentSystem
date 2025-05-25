package com.learning.authservice.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String username;
    private String password;
    private String role;
    // Getters and Setters
}

