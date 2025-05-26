package com.example.instructor_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleDTO {
    private Long id;
    private Long batchId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
}
