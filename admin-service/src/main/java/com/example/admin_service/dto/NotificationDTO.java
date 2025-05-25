package com.example.admin_service.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private String userId; // Optional for mass announcements
    private String message;
    private String type; // e.g., "EMAIL", "SMS", "BROADCAST"
}
