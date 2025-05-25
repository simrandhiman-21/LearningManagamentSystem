package com.example.batch_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BatchDTO {
    private Long id;
    private String name;
    private Long courseId;
    private Long instructorId;
    private List<String> studentIds;
    private List<Long> scheduleIds;
}
