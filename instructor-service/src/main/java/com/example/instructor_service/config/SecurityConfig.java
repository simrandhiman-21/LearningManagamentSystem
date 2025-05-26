package com.example.instructor_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/instructors").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/instructors/{instructorId}").hasAnyAuthority("ROLE_ADMIN", "ROLE_INSTRUCTOR")
                        .requestMatchers("/api/instructors/{instructorId}/courses/{courseId}").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/instructors/{instructorId}/batches/{batchId}").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/instructors/{instructorId}/schedule").hasAnyAuthority("ROLE_ADMIN", "ROLE_INSTRUCTOR")
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());
        return http.build();
    }
}
