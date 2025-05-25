package com.learning.userservice.service;

import com.learning.userservice.entity.UserProfile;
import com.learning.userservice.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserProfileRepository userRepo;

    @Override
    public String createProfile(UserProfile profile) {
        userRepo.save(profile);
        return "Profile created";
    }

    @Override
    public Optional<UserProfile> getProfileByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public String updateProfile(String username, UserProfile updated) {
        return userRepo.findByUsername(username).map(profile -> {
            profile.setFullName(updated.getFullName());
            profile.setEmail(updated.getEmail());
            profile.setPhone(updated.getPhone());
            userRepo.save(profile);
            return "Profile updated";
        }).orElse("Profile not found");
    }
}

