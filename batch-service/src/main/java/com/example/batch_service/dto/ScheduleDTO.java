package com.example.batch_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ScheduleDTO {
    private Long id;
    private Long batchId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
}
