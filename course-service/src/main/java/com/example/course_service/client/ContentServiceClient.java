package com.example.course_service.client;


import com.example.course_service.dto.ContentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "content-service")
public interface ContentServiceClient {
    @GetMapping("/api/contents/{contentId}")
    ContentDTO getContentById(@PathVariable("contentId") Long contentId);
}
