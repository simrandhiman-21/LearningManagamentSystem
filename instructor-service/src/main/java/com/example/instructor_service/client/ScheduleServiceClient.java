package com.example.instructor_service.client;

import com.example.instructor_service.dto.ScheduleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "schedule-service")
public interface ScheduleServiceClient {
    @GetMapping("/api/schedules/batch/{batchId}")
    ScheduleDTO getScheduleByBatchId(@PathVariable("batchId") Long batchId);
}