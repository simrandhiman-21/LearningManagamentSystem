package com.example.notification_service.dto;

import lombok.Data;

@Data
public class NotificationEvent {
    private String eventType; // e.g., OTP_GENERATED, CLASS_SCHEDULED
    private String recipientId; // Email or phone number
    private String content; // Notification content
}
