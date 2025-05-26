package com.example.instructor_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InstructorDTO {
    private Long id;
    private String name;
    private String email;
    private List<Long> courseIds;
    private List<Long> batchIds;
}
