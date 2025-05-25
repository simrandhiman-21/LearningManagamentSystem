package com.example.admin_service.client;

import com.example.admin_service.dto.BatchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "batch-service")
public interface BatchServiceClient {
    @PostMapping("/api/batches")
    void createBatch(@RequestBody BatchDTO batch);
}
