package com.example.payment_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentId;
    private Long courseId;
    private Double amount;
    private String paymentStatus; // e.g., "PENDING", "COMPLETED", "FAILED"
    private String transactionId; // From payment gateway
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
