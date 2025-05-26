package com.example.exam_service.client;

import com.example.exam_service.dto.SubmissionReportDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "reporting-service")
public interface ReportingServiceClient {
    @PostMapping("/api/reports/submission")
    void reportSubmission(@RequestBody SubmissionReportDTO report);
}