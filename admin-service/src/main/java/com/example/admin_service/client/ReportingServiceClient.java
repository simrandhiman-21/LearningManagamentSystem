package com.example.admin_service.client;

import com.example.admin_service.dto.ReportDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "reporting-service")
public interface ReportingServiceClient {
    @GetMapping("/api/reports/summary")
    ReportDTO getSystemSummary();
}
