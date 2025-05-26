package com.example.exam_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubmissionReportDTO {
    private Long quizId;
    private String studentId;
    private Integer score;
    private LocalDateTime submittedAt;
}