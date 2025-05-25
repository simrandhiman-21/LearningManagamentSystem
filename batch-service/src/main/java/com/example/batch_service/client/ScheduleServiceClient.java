package com.example.batch_service.client;

import com.example.batch_service.dto.ScheduleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "schedule-service")
public interface ScheduleServiceClient {
    @PostMapping("/api/schedules")
    ScheduleDTO createSchedule(@RequestBody ScheduleDTO schedule);
}
