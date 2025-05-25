package com.example.admin_service.client;


import com.example.admin_service.dto.CourseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "course-service")
public interface CourseServiceClient {
    @PostMapping("/api/courses")
    void createCourse(@RequestBody CourseDTO course);
}
