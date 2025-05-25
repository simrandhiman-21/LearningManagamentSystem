package com.example.admin_service.service;

import com.example.admin_service.client.*;
import com.example.admin_service.dto.*;
import com.example.admin_service.model.AuditLog;
import com.example.admin_service.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AuditLogRepository auditLogRepository;
    private final UserServiceClient userServiceClient;
    private final BatchServiceClient batchServiceClient;
    private final CourseServiceClient courseServiceClient;
    private final NotificationServiceClient notificationServiceClient;
    private final ReportingServiceClient reportingServiceClient;
    private final RabbitTemplate rabbitTemplate;

    public void createUser(UserDTO user, String adminId) {
        userServiceClient.createUser(user);
        logAudit(adminId, "CREATE_USER", "User", user.getUserId(), "Created user: " + user.getName());
    }

    public void createBatch(BatchDTO batch, String adminId) {
        batchServiceClient.createBatch(batch);
        logAudit(adminId, "CREATE_BATCH", "Batch", batch.getId() != null ? batch.getId().toString() : null, "Created batch: " + batch.getName());
    }

    public void createCourse(CourseDTO course, String adminId) {
        courseServiceClient.createCourse(course);
        logAudit(adminId, "CREATE_COURSE", "Course", course.getId() != null ? course.getId().toString() : null, "Created course: " + course.getTitle());
    }

    public void sendMassAnnouncement(String message, String adminId) {
        NotificationDTO notification = new NotificationDTO();
        notification.setMessage(message);
        notification.setType("BROADCAST");
        rabbitTemplate.convertAndSend("notification.queue", notification);
        logAudit(adminId, "SEND_ANNOUNCEMENT", "Notification", null, "Sent mass announcement: " + message);
    }

    public ReportDTO getSystemSummary(String adminId) {
        ReportDTO report = reportingServiceClient.getSystemSummary();
        logAudit(adminId, "VIEW_REPORT", "Report", null, "Viewed system summary");
        return report;
    }

    public List<AuditLog> getAuditLogs(String adminId) {
        return auditLogRepository.findByAdminId(adminId);
    }

    private void logAudit(String adminId, String action, String entityType, String entityId, String details) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAdminId(adminId);
        auditLog.setAction(action);
        auditLog.setEntityType(entityType);
        auditLog.setEntityId(entityId);
        auditLog.setTimestamp(LocalDateTime.now());
        auditLog.setDetails(details);
        auditLogRepository.save(auditLog);
    }
}