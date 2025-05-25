package com.example.course_service.controller;

import com.example.course_service.dto.CourseDTO;
import com.example.course_service.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO, @AuthenticationPrincipal Jwt jwt) {
        String adminId = jwt.getSubject();
        return ResponseEntity.ok(courseService.createCourse(courseDTO, adminId));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }
}
//Database Setup: Create a MySQL database (lms_course_db) and ensure credentials match application.yml.
//Config Server and Eureka: Ensure Config Server (http://localhost:8888) and Eureka Server (http://localhost:8761) are running.
//        RabbitMQ: Ensure RabbitMQ is running (docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management).
//Auth Service: Ensure the Auth Service provides JWT tokens with ROLE_ADMIN, ROLE_INSTRUCTOR, or ROLE_STUDENT.
//Run the Service: Use mvn spring-boot:run or your IDE.