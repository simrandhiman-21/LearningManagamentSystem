package com.learning.authservice.client;

import com.learning.authservice.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users/username/{username}")
    UserResponse getUserByUsername(@PathVariable String username);
}

