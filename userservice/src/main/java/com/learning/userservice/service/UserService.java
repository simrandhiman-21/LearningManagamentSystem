package com.learning.userservice.service;


import com.learning.userservice.entity.UserProfile;
import java.util.Optional;

public interface UserService {
    String createProfile(UserProfile profile);
    Optional<UserProfile> getProfileByUsername(String username);
    String updateProfile(String username, UserProfile updated);
}

