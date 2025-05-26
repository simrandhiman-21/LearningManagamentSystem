package com.example.payment_service.controller;

import com.example.payment_service.dto.PaymentDTO;
import com.example.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDTO> initiatePayment(@RequestBody PaymentDTO paymentDTO, @AuthenticationPrincipal Jwt jwt) {
        String studentId = jwt.getSubject();
        return ResponseEntity.ok(paymentService.initiatePayment(paymentDTO, studentId));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }

    @GetMapping("/student")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByStudentId(@AuthenticationPrincipal Jwt jwt) {
        String studentId = jwt.getSubject();
        return ResponseEntity.ok(paymentService.getPaymentsByStudentId(studentId));
    }
}
//Database Setup: Create lms_payment_db in MySQL and verify application.yml credentials.
//Config and Eureka: Ensure Config Server (http://localhost:8888) and Eureka Server (http://localhost:8761) are running.
//        RabbitMQ: Run RabbitMQ (docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management).
//Auth Service: Ensure JWT tokens with ROLE_STUDENT or ROLE_ADMIN are available.
//Run the Service: Use mvn spring-boot:run or IDE.
