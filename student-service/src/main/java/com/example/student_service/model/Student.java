package com.example.student_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId; // Reference to User Service ID
    private String name;
    private String email;
    private String phone;

    @ElementCollection
    private List<Long> enrolledBatchIds; // References to Batch Service
    @ElementCollection
    private List<Long> enrolledCourseIds; // References to Course Service
}
