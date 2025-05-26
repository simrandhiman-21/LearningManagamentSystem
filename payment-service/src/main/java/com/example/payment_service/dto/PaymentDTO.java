package com.example.payment_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private Long id;
    private String studentId;
    private Long courseId;
    private Double amount;
    private String paymentStatus;
    private String transactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}