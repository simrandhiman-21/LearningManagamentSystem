package com.example.exam_service.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Question {
    private String questionId;
    private String text;
    private String correctAnswer;
}