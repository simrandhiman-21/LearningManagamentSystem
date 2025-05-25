package com.example.admin_service.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String userId;
    private String name;
    private String email;
    private String role; // e.g., "STUDENT", "INSTRUCTOR", "ADMIN"
}
