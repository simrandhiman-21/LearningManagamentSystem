package com.example.exam_service.security;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String NOTIFICATION_QUEUE = "notification.queue";
    public static final String QUIZ_SUBMISSION_QUEUE = "quiz.submission.queue";

    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE, true);
    }

    @Bean
    public Queue quizSubmissionQueue() {
        return new Queue(QUIZ_SUBMISSION_QUEUE, true);
    }
}
