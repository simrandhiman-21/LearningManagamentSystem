package com.example.batch_service.client;


import com.example.batch_service.dto.StudentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student-service")
public interface StudentServiceClient {
    @GetMapping("/api/students/profile/{userId}")
    StudentDTO getStudentById(@PathVariable("userId") String userId);
}
