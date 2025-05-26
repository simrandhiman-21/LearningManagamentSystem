package com.example.exam_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quizId;
    private String studentId;
    @ElementCollection
    private List<Answer> answers;
    private LocalDateTime submittedAt;
    private Integer score;
}
