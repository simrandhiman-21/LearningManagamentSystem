package com.example.batch_service.controller;

import com.example.batch_service.dto.BatchDTO;
import com.example.batch_service.dto.ScheduleDTO;
import com.example.batch_service.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/batches")
@RequiredArgsConstructor
public class BatchController {
    private final BatchService batchService;

    @PostMapping
    public ResponseEntity<BatchDTO> createBatch(@RequestBody BatchDTO batchDTO, @AuthenticationPrincipal Jwt jwt) {
        String adminId = jwt.getSubject();
        return ResponseEntity.ok(batchService.createBatch(batchDTO, adminId));
    }

    @GetMapping("/{batchId}")
    public ResponseEntity<BatchDTO> getBatchById(@PathVariable Long batchId) {
        return ResponseEntity.ok(batchService.getBatchById(batchId));
    }

    @PostMapping("/{batchId}/schedules")
    public ResponseEntity<String> createSchedule(@PathVariable Long batchId, @RequestBody ScheduleDTO scheduleDTO) {
        batchService.createSchedule(batchId, scheduleDTO);
        return ResponseEntity.ok("Schedule created successfully");
    }
}
//Testing and Running
//Database Setup: Create a MySQL database (lms_batch_db) and ensure credentials match application.yml.
//Config Server and Eureka: Ensure Config Server (http://localhost:8888) and Eureka Server (http://localhost:8761) are running.
//        RabbitMQ: Ensure RabbitMQ is running (docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management).
//Auth Service: Ensure the Auth Service provides JWT tokens with ROLE_ADMIN, ROLE_INSTRUCTOR, or ROLE_STUDENT.
//Run the Service: Use mvn spring-boot:run or your IDE.
