package com.learning.userservice.controllers;

import com.learning.userservice.entity.UserProfile;
import com.learning.userservice.repository.UserProfileRepository;
import com.learning.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createProfile(@RequestBody UserProfile profile) {
        return ResponseEntity.ok(userService.createProfile(profile));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable String username) {
        return userService.getProfileByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{username}")
    public ResponseEntity<String> updateProfile(@PathVariable String username, @RequestBody UserProfile updated) {
        String result = userService.updateProfile(username, updated);
        if ("Profile updated".equals(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

