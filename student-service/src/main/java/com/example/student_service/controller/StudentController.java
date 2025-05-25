package com.example.student_service.controller;

import com.example.student_service.dto.CourseDTO;
import com.example.student_service.dto.QuizSubmissionDTO;
import com.example.student_service.model.Student;
import com.example.student_service.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    @Autowired
    private final StudentService studentService;

    @GetMapping("/profile/{userId}")
    public ResponseEntity<Student> getProfile(@PathVariable String userId) {
        return ResponseEntity.ok(studentService.getStudentProfile(userId));
    }

    @PostMapping("/enroll/{userId}/{batchId}")
    public ResponseEntity<String> enrollInBatch(@PathVariable String userId, @PathVariable Long batchId) {
        studentService.enrollInBatch(userId, batchId);
        return ResponseEntity.ok("Enrolled successfully");
    }

    @GetMapping("/courses/{userId}/{courseId}")
    public ResponseEntity<CourseDTO> getEnrolledCourse(@PathVariable String userId, @PathVariable Long courseId) {
        return ResponseEntity.ok(studentService.getEnrolledCourse(userId, courseId));
    }

    @PostMapping("/quizzes/{userId}/{quizId}")
    public ResponseEntity<String> submitQuiz(@PathVariable String userId, @PathVariable Long quizId, @RequestBody QuizSubmissionDTO submission) {
        // Validate student and quiz via Exam Service
        // Publish event to Exam Service and Notification Service
        return ResponseEntity.ok("Quiz submitted successfully");
    }
}
//. Testing and Running
//Database Setup: Create a MySQL database (lms_student_db) and ensure the credentials match application.yml.
//Config Server and Eureka: Ensure the Config Server and Eureka Server are running (URLs: http://localhost:8888 and http://localhost:8761).
//        RabbitMQ: Ensure RabbitMQ is running locally or in a container (docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management).
//Run the Service: Use mvn spring-boot:run or your IDE to start the Student Service.
//Test Endpoints: Use Postman or curl to test endpoints like:
//GET http://localhost:8082/api/students/profile/{userId}
//POST http://localhost:8082/api/students/enroll/{userId}/{batchId}