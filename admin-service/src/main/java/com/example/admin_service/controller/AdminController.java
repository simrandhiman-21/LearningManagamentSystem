package com.example.admin_service.controller;

import com.example.admin_service.dto.BatchDTO;
import com.example.admin_service.dto.CourseDTO;
import com.example.admin_service.dto.ReportDTO;
import com.example.admin_service.dto.UserDTO;
import com.example.admin_service.model.AuditLog;
import com.example.admin_service.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody UserDTO user, @AuthenticationPrincipal Jwt jwt) {
        String adminId = jwt.getSubject();
        adminService.createUser(user, adminId);
        return ResponseEntity.ok("User created successfully");
    }

    @PostMapping("/batches")
    public ResponseEntity<String> createBatch(@RequestBody BatchDTO batch, @AuthenticationPrincipal Jwt jwt) {
        String adminId = jwt.getSubject();
        adminService.createBatch(batch, adminId);
        return ResponseEntity.ok("Batch created successfully");
    }

    @PostMapping("/courses")
    public ResponseEntity<String> createCourse(@RequestBody CourseDTO course, @AuthenticationPrincipal Jwt jwt) {
        String adminId = jwt.getSubject();
        adminService.createCourse(course, adminId);
        return ResponseEntity.ok("Course created successfully");
    }

    @PostMapping("/announcements")
    public ResponseEntity<String> sendMassAnnouncement(@RequestBody String message, @AuthenticationPrincipal Jwt jwt) {
        String adminId = jwt.getSubject();
        adminService.sendMassAnnouncement(message, adminId);
        return ResponseEntity.ok("Announcement sent successfully");
    }

    @GetMapping("/reports/summary")
    public ResponseEntity<ReportDTO> getSystemSummary(@AuthenticationPrincipal Jwt jwt) {
        String adminId = jwt.getSubject();
        return ResponseEntity.ok(adminService.getSystemSummary(adminId));
    }

    @GetMapping("/audit-logs")
    public ResponseEntity<List<AuditLog>> getAuditLogs(@AuthenticationPrincipal Jwt jwt) {
        String adminId = jwt.getSubject();
        return ResponseEntity.ok(adminService.getAuditLogs(adminId));
    }
}
//10. Testing and Running
//Database Setup: Create a MySQL database (lms_admin_db) and ensure credentials match application.yml.
//Config Server and Eureka: Ensure Config Server (http://localhost:8888) and Eureka Server (http://localhost:8761) are running.
//        RabbitMQ: Ensure RabbitMQ is running (docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management).
//Auth Service: Ensure the Auth Service is running and provides JWT tokens with ROLE_ADMIN.
//Run the Service: Use mvn spring-boot:run or your IDE to start the Admin Service.
//Test Endpoints: Use Postman or curl with a valid JWT token:
//POST http://localhost:8083/api/admin/users (create user)
//POST http://localhost:8083/api/admin/batches (create batch)
//POST http://localhost:8083/api/admin/announcements (send announcement)
//GET http://localhost:8083/api/admin/reports/summary (view report)
