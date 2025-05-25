package com.example.admin_service.dto;

import lombok.Data;

@Data
public class BatchDTO {
    private Long id;
    private String name;
    private Long courseId;
    private Long instructorId;
}
