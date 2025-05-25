package com.example.batch_service.dto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NotificationDTO {
    private String userId;
    private String message;
    private String type; // e.g., "EMAIL", "SMS"
}
