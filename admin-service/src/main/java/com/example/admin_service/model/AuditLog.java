package com.example.admin_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adminId; // Reference to User Service ID
    private String action; // e.g., "CREATE_BATCH", "UPDATE_USER"
    private String entityType; // e.g., "Batch", "Course"
    private String entityId; // ID of the affected entity
    private LocalDateTime timestamp;
    private String details; // Additional details about the action
}