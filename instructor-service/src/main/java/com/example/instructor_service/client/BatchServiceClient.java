package com.example.instructor_service.client;

import com.example.instructor_service.dto.BatchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "batch-service")
public interface BatchServiceClient {
    @GetMapping("/api/batches/{batchId}")
    BatchDTO getBatchById(@PathVariable("batchId") Long batchId);
}