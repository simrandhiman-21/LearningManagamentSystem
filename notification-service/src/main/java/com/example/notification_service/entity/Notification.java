    package com.example.notification_service.entity;

    import jakarta.persistence.Entity;
    import jakarta.persistence.Id;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import lombok.Data;
    import java.time.LocalDateTime;

    @Entity
    @Data
    public class Notification {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String recipientId; // User ID from User Service
        private String type; // e.g., EMAIL, SMS, IN_APP
        private String content;
        private String status; // e.g., SENT, FAILED
        private LocalDateTime sentAt;
    }
