package com.example.instructor_service.client;

import com.example.instructor_service.dto.CourseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-service")
public interface CourseServiceClient {
    @GetMapping("/api/courses/{courseId}")
    CourseDTO getCourseById(@PathVariable("courseId") Long courseId);
}