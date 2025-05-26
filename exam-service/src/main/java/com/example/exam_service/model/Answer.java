package com.example.exam_service.model;


import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Answer {
    private String questionId;
    private String answer;
}
