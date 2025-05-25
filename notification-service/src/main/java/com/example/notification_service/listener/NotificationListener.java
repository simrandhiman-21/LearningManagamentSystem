package com.example.notification_service.listener;

import com.example.notification_service.dto.NotificationEvent;
import com.example.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${spring.rabbitmq.queue.notification}")
    public void handleNotificationEvent(NotificationEvent event) {
        switch (event.getEventType()) {
            case "OTP_GENERATED":
                notificationService.sendEmailNotification(
                        event.getRecipientId(),
                        "Your OTP Code",
                        "Your OTP is: " + event.getContent()
                );
                break;
            case "CLASS_SCHEDULED":
                notificationService.sendEmailNotification(
                        event.getRecipientId(),
                        "Class Scheduled",
                        "A new class has been scheduled: " + event.getContent()
                );
                break;
            case "RESULT_PUBLISHED":
                notificationService.sendEmailNotification(
                        event.getRecipientId(),
                        "Exam Results Published",
                        "Your exam results are available: " + event.getContent()
                );
                break;
            case "PAYMENT_SUCCESS":
                notificationService.sendEmailNotification(
                        event.getRecipientId(),
                        "Payment Confirmation",
                        "Payment successful: " + event.getContent()
                );
                break;
            case "FEEDBACK_SUBMITTED":
                notificationService.sendEmailNotification(
                        event.getRecipientId(),
                        "Feedback Received",
                        "Thank you for your feedback: " + event.getContent()
                );
                break;
            default:
                // Log unsupported event
        }
    }
}
