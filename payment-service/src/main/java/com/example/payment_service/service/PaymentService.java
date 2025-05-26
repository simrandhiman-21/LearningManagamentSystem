package com.example.payment_service.service;

import com.example.payment_service.client.CourseServiceClient;
import com.example.payment_service.client.NotificationServiceClient;
import com.example.payment_service.client.PaymentGatewayClient;
import com.example.payment_service.client.StudentServiceClient;
import com.example.payment_service.dto.CourseDTO;
import com.example.payment_service.dto.NotificationDTO;
import com.example.payment_service.dto.PaymentDTO;
import com.example.payment_service.dto.StudentDTO;
import com.example.payment_service.entity.Payment;
import com.example.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final StudentServiceClient studentServiceClient;
    private final CourseServiceClient courseServiceClient;
    private final NotificationServiceClient notificationServiceClient;
    private final PaymentGatewayClient paymentGatewayClient;
    private final RabbitTemplate rabbitTemplate;

    public PaymentDTO initiatePayment(PaymentDTO paymentDTO, String studentId) {
        // Validate student
        StudentDTO student = studentServiceClient.getStudentById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        // Validate course and fee
        CourseDTO course = courseServiceClient.getCourseById(paymentDTO.getCourseId());
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        if (!course.getFee().equals(paymentDTO.getAmount())) {
            throw new RuntimeException("Payment amount does not match course fee");
        }

        // Process payment via gateway
        String transactionId = paymentGatewayClient.processPayment(studentId, paymentDTO.getAmount(), paymentDTO.getCourseId().toString())
                .block(); // Blocking for simplicity; use reactive in production

        // Save payment
        Payment payment = new Payment();
        payment.setStudentId(studentId);
        payment.setCourseId(paymentDTO.getCourseId());
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentStatus("COMPLETED"); // Mocked as completed
        payment.setTransactionId(transactionId);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());
        payment = paymentRepository.save(payment);

        // Update DTO
        paymentDTO.setId(payment.getId());
        paymentDTO.setPaymentStatus(payment.getPaymentStatus());
        paymentDTO.setTransactionId(payment.getTransactionId());
        paymentDTO.setCreatedAt(payment.getCreatedAt());
        paymentDTO.setUpdatedAt(payment.getUpdatedAt());

        // Publish notification
        NotificationDTO notification = new NotificationDTO();
        notification.setUserId(studentId);
        notification.setMessage("Payment successful for course: " + course.getTitle());
        notification.setType("EMAIL");
        rabbitTemplate.convertAndSend("notification.queue", notification);

        return paymentDTO;
    }

    public PaymentDTO getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setStudentId(payment.getStudentId());
        paymentDTO.setCourseId(payment.getCourseId());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setPaymentStatus(payment.getPaymentStatus());
        paymentDTO.setTransactionId(payment.getTransactionId());
        paymentDTO.setCreatedAt(payment.getCreatedAt());
        paymentDTO.setUpdatedAt(payment.getUpdatedAt());
        return paymentDTO;
    }

    public List<PaymentDTO> getPaymentsByStudentId(String studentId) {
        return paymentRepository.findByStudentId(studentId).stream().map(payment -> {
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setId(payment.getId());
            paymentDTO.setStudentId(payment.getStudentId());
            paymentDTO.setCourseId(payment.getCourseId());
            paymentDTO.setAmount(payment.getAmount());
            paymentDTO.setPaymentStatus(payment.getPaymentStatus());
            paymentDTO.setTransactionId(payment.getTransactionId());
            paymentDTO.setCreatedAt(payment.getCreatedAt());
            paymentDTO.setUpdatedAt(payment.getUpdatedAt());
            return paymentDTO;
        }).toList();
    }
}
