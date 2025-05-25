package com.example.batch_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long courseId; // Reference to Course Service
    private Long instructorId; // Reference to Instructor Service
    @ElementCollection
    private List<String> studentIds; // References to Student Service (userIds)
    @ElementCollection
    private List<Long> scheduleIds; // References to Schedule Service
}
