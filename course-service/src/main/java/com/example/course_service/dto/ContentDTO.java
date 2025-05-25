package com.example.course_service.dto;


import lombok.Data;

@Data
public class ContentDTO {
    private Long id;
    private String title;
    private String type; // e.g., "VIDEO", "PDF"
}
