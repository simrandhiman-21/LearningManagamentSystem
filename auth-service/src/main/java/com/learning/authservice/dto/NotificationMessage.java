package com.learning.authservice.dto;

import lombok.Data;

@Data
public class NotificationMessage {
    private String to;
    private String message;

    public NotificationMessage(String to, String message) {
        this.to = to;
        this.message = message;
    }

    // Getters and Setters
}

