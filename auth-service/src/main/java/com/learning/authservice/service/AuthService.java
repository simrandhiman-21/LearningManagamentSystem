package com.learning.authservice.service;

import com.learning.authservice.client.UserClient;
import com.learning.authservice.dto.LoginRequest;
import com.learning.authservice.dto.NotificationMessage;
import com.learning.authservice.dto.UserResponse;
import com.learning.authservice.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import com.learning.authservice.messaging.NotificationPublisher;



import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private OtpService otpService;

    @Autowired
    private NotificationPublisher notificationPublisher;

    public Map<String, String> login(LoginRequest request) {
        UserResponse user = userClient.getUserByUsername(request.getUsername());

        if (user == null || !request.getPassword().equals(user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        return Map.of("token", token);
    }

    public void sendOtp(String email) {
        String otp = otpService.generateOtp(email);
        NotificationMessage msg = new NotificationMessage(email, "Your OTP is: " + otp);
        notificationPublisher.send(msg);
    }
}
