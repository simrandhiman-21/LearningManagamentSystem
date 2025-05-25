package com.example.student_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuizSubmissionDTO {
    private Long quizId; // ID of the quiz being submitted
    private String userId; // ID of the student (linked to User Service)
    private List<AnswerDTO> answers; // List of answers provided by the student
    private LocalDateTime submittedAt; // Timestamp of submission

    @Data
    public static class AnswerDTO {
        private Long questionId; // ID of the question
        private String answer; // Student's answer (e.g., text, option ID, etc.)
    }
}
