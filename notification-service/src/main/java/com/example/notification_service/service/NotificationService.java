package com.example.notification_service.service;


import com.example.notification_service.entity.Notification;
import com.example.notification_service.repository.NotificationRepository;
import com.example.notification_service.dto.NotificationEvent;
import com.example.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    @Autowired
    private final JavaMailSender mailSender;
    @Autowired
    private final NotificationRepository notificationRepository;

    public void sendEmailNotification(String recipientEmail, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);

        // Log notification
        Notification notification = new Notification();
        notification.setRecipientId(recipientEmail);
        notification.setType("EMAIL");
        notification.setContent(content);
        notification.setStatus("SENT");
        notification.setSentAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    // Placeholder for SMS or in-app notification
    public void sendSmsNotification(String phoneNumber, String content) {
        // Integrate with SMS gateway (e.g., Twilio)
        // Log notification
        Notification notification = new Notification();
        notification.setRecipientId(phoneNumber);
        notification.setType("SMS");
        notification.setContent(content);
        notification.setStatus("SENT");
        notification.setSentAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }
}
