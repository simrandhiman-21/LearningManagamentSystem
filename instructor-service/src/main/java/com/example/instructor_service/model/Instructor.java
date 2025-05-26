package com.example.instructor_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Instructor {
    @Id
    private Long id; // Matches userId from User Service
    private String name;
    private String email;
    @ElementCollection
    private List<Long> courseIds; // Assigned courses
    @ElementCollection
    private List<Long> batchIds; // Assigned batches
}
