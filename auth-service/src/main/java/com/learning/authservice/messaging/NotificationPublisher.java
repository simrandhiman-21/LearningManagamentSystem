package com.learning.authservice.messaging;

import com.learning.authservice.dto.NotificationMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.notification.exchange}")
    private String exchange;

    @Value("${rabbitmq.notification.routingkey}")
    private String routingKey;

    public void send(NotificationMessage message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}

