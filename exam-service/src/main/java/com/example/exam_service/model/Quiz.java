package com.example.exam_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Long courseId;
    private Long instructorId;
    @ElementCollection
    private List<Question> questions;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}