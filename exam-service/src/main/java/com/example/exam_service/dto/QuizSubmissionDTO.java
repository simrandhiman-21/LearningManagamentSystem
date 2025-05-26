package com.example.exam_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuizSubmissionDTO {
    private Long quizId;
    private String userId;
    private List<AnswerDTO> answers;
    private LocalDateTime submittedAt;

    @Data
    public static class AnswerDTO {
        private String questionId;
        private String answer;
    }
}
