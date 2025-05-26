package com.example.instructor_service.controller;

import com.example.instructor_service.dto.InstructorDTO;
import com.example.instructor_service.dto.ScheduleDTO;
import com.example.instructor_service.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructors")
@RequiredArgsConstructor
public class InstructorController {
    private final InstructorService instructorService;

    @PostMapping
    public ResponseEntity<InstructorDTO> createInstructor(@RequestBody InstructorDTO instructorDTO, @AuthenticationPrincipal Jwt jwt) {
        String adminId = jwt.getSubject();
        return ResponseEntity.ok(instructorService.createInstructor(instructorDTO, adminId));
    }

    @GetMapping("/{instructorId}")
    public ResponseEntity<InstructorDTO> getInstructorById(@PathVariable Long instructorId) {
        return ResponseEntity.ok(instructorService.getInstructorById(instructorId));
    }

    @PostMapping("/{instructorId}/courses/{courseId}")
    public ResponseEntity<String> assignCourse(@PathVariable Long instructorId, @PathVariable Long courseId, @AuthenticationPrincipal Jwt jwt) {
        String adminId = jwt.getSubject();
        instructorService.assignCourse(instructorId, courseId, adminId);
        return ResponseEntity.ok("Course assigned successfully");
    }

    @PostMapping("/{instructorId}/batches/{batchId}")
    public ResponseEntity<String> assignBatch(@PathVariable Long instructorId, @PathVariable Long batchId, @AuthenticationPrincipal Jwt jwt) {
        String adminId = jwt.getSubject();
        instructorService.assignBatch(instructorId, batchId, adminId);
        return ResponseEntity.ok("Batch assigned successfully");
    }

    @GetMapping("/{instructorId}/schedule")
    public ResponseEntity<ScheduleDTO> getInstructorSchedule(@PathVariable Long instructorId) {
        return ResponseEntity.ok(instructorService.getInstructorSchedule(instructorId));
    }
}
//Database Setup: Create lms_instructor_db in MySQL and verify application.yml credentials.
//Config and Eureka: Ensure Config Server (http://localhost:8888) and Eureka Server (http://localhost:8761) are running.
//        RabbitMQ: Run RabbitMQ (docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management).
//Auth Service: Ensure JWT tokens with ROLE_ADMIN or ROLE_INSTRUCTOR are available.
//Run the Service: Use mvn spring-boot:run or IDE.
