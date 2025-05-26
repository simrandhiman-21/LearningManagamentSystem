package com.example.exam_service.service;

import com.example.exam_service.client.CourseServiceClient;
import com.example.exam_service.client.NotificationServiceClient;
import com.example.exam_service.client.ReportingServiceClient;
import com.example.exam_service.client.StudentServiceClient;
import com.example.exam_service.dto.*;
import com.example.exam_service.model.Question;
import com.example.exam_service.model.Answer;
import com.example.exam_service.model.Quiz;
import com.example.exam_service.model.Submission;
import com.example.exam_service.repository.QuizRepository;
import com.example.exam_service.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {
    private final QuizRepository quizRepository;
    private final SubmissionRepository submissionRepository;
    private final StudentServiceClient studentServiceClient;
    private final CourseServiceClient courseServiceClient;
    private final NotificationServiceClient notificationServiceClient;
    private final ReportingServiceClient reportingServiceClient;
    private final RabbitTemplate rabbitTemplate;

    public QuizDTO createQuiz(QuizDTO quizDTO, String adminId) {
        // Validate course
        CourseDTO course = courseServiceClient.getCourseById(quizDTO.getCourseId());
        if (course == null) {
            throw new RuntimeException("Course not found");
        }

        // Save quiz
        Quiz quiz = new Quiz();
        quiz.setTitle(quizDTO.getTitle());
        quiz.setCourseId(quizDTO.getCourseId());
        quiz.setInstructorId(quizDTO.getInstructorId());
        quiz.setQuestions(quizDTO.getQuestions().stream().map(q -> {
            Question question = new Question(); // Changed from Quiz.Question
            question.setQuestionId(q.getQuestionId());
            question.setText(q.getText());
            question.setCorrectAnswer(q.getCorrectAnswer());
            return question;
        }).toList());
        quiz.setStartTime(quizDTO.getStartTime());
        quiz.setEndTime(quizDTO.getEndTime());
        quiz = quizRepository.save(quiz);

        // Update DTO
        quizDTO.setId(quiz.getId());

        // Publish notification
        NotificationDTO notification = new NotificationDTO();
        notification.setMessage("New quiz created: " + quiz.getTitle());
        notification.setType("EMAIL");
        rabbitTemplate.convertAndSend("notification.queue", notification);

        return quizDTO;
    }

    public void submitQuiz(QuizSubmissionDTO submission) {
        // Validate student
        StudentDTO student = studentServiceClient.getStudentById(submission.getUserId());
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        // Validate quiz
        Quiz quiz = quizRepository.findById(submission.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        // Validate submission time
        if (submission.getSubmittedAt().isAfter(quiz.getEndTime())) {
            throw new RuntimeException("Submission is late");
        }

        // Grade submission
        int score = 0;
        for (QuizSubmissionDTO.AnswerDTO answer : submission.getAnswers()) {
            Question question = quiz.getQuestions().stream() // Changed from Quiz.Question
                    .filter(q -> q.getQuestionId().equals(answer.getQuestionId()))
                    .findFirst()
                    .orElse(null);
            if (question != null && question.getCorrectAnswer().equals(answer.getAnswer())) {
                score += 10; // Example scoring: 10 points per correct answer
            }
        }

        // Save submission
        Submission submissionEntity = new Submission();
        submissionEntity.setQuizId(submission.getQuizId());
        submissionEntity.setStudentId(submission.getUserId());
        submissionEntity.setAnswers(submission.getAnswers().stream().map(a -> {
            Answer answer = new Answer(); // Changed from Submission.Answer
            answer.setQuestionId(a.getQuestionId());
            answer.setAnswer(a.getAnswer());
            return answer;
        }).toList());
        submissionEntity.setSubmittedAt(submission.getSubmittedAt());
        submissionEntity.setScore(score);
        submissionRepository.save(submissionEntity);

        // Send notification
        NotificationDTO notification = new NotificationDTO();
        notification.setUserId(submission.getUserId());
        notification.setMessage("Quiz submitted successfully. Score: " + score);
        notification.setType("EMAIL");
        rabbitTemplate.convertAndSend("notification.queue", notification);

        // Report to Reporting Service
        SubmissionReportDTO report = new SubmissionReportDTO();
        report.setQuizId(submission.getQuizId());
        report.setStudentId(submission.getUserId());
        report.setScore(score);
        report.setSubmittedAt(submission.getSubmittedAt());
        reportingServiceClient.reportSubmission(report);
    }

    public QuizDTO getQuizById(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setId(quiz.getId());
        quizDTO.setTitle(quiz.getTitle());
        quizDTO.setCourseId(quiz.getCourseId());
        quizDTO.setInstructorId(quiz.getInstructorId());
        quizDTO.setQuestions(quiz.getQuestions().stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestionId(q.getQuestionId());
            questionDTO.setText(q.getText());
            questionDTO.setCorrectAnswer(q.getCorrectAnswer());
            return questionDTO;
        }).toList());
        quizDTO.setStartTime(quiz.getStartTime());
        quizDTO.setEndTime(quiz.getEndTime());
        return quizDTO;
    }
}