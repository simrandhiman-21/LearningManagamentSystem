package com.example.instructor_service.client;

import com.example.instructor_service.dto.NotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service")
public interface NotificationServiceClient {
    @PostMapping("/api/notifications")
    void sendNotification(@RequestBody NotificationDTO notification);
}