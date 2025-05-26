package com.example.exam_service.repository;

import com.example.exam_service.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByStudentIdAndQuizId(String studentId, Long quizId);
}