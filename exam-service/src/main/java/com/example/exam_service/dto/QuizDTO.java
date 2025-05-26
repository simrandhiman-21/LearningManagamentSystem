package com.example.exam_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuizDTO {
    private Long id;
    private String title;
    private Long courseId;
    private Long instructorId;
    private List<QuestionDTO> questions;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}