package com.example.exam_service.dto;


import lombok.Data;

@Data
public class QuestionDTO {
    private String questionId;
    private String text;
    private String correctAnswer;
}
