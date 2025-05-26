package com.example.exam_service.controller;

import com.example.exam_service.dto.QuizDTO;
import com.example.exam_service.dto.QuizSubmissionDTO;
import com.example.exam_service.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class ExamController {
    private final ExamService examService;

    @PostMapping
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizDTO quizDTO, @AuthenticationPrincipal Jwt jwt) {
        String adminId = jwt.getSubject();
        return ResponseEntity.ok(examService.createQuiz(quizDTO, adminId));
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable Long quizId) {
        return ResponseEntity.ok(examService.getQuizById(quizId));
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitQuiz(@RequestBody QuizSubmissionDTO submission) {
        examService.submitQuiz(submission);
        return ResponseEntity.ok("Quiz submitted successfully");
    }
}
//Database Setup: Create lms_exam_db in MySQL and verify application.yml credentials.
//Config Server and Eureka: Ensure Config Server (http://localhost:8888) and Eureka Server (http://localhost:8761) are running.
//        RabbitMQ: Run RabbitMQ (docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management).
//Auth Service: Ensure JWT tokens with ROLE_ADMIN or ROLE_STUDENT are available.
//Run the Service: Use mvn spring-boot:run or IDE.
