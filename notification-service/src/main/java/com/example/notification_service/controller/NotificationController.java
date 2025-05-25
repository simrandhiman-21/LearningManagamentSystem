package com.example.notification_service.controller;

 import com.example.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    @Autowired
    private final NotificationService notificationService;

    @PostMapping("/send-email")
    public void sendEmail(@RequestParam String recipient, @RequestParam String subject, @RequestParam String content) {
        notificationService.sendEmailNotification(recipient, subject, content);
    }

    @PostMapping("/send-sms")
    public void sendSms(@RequestParam String phoneNumber, @RequestParam String content) {
        notificationService.sendSmsNotification(phoneNumber, content);
    }
}
