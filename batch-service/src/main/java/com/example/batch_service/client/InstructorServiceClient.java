package com.example.batch_service.client;

import com.example.batch_service.dto.InstructorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "instructor-service")
public interface InstructorServiceClient {
    @GetMapping("/api/instructors/{instructorId}")
    InstructorDTO getInstructorById(@PathVariable("instructorId") Long instructorId);
}
