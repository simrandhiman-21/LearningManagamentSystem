package com.example.student_service.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private String userId;
    private String message;
    private String type; // e.g., EMAIL, SMS
}
