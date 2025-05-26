package com.example.payment_service.dto;

import lombok.Data;

@Data
public class CourseDTO {
    private Long id;
    private String title;
    private Double fee; // Course fee for payment
}
