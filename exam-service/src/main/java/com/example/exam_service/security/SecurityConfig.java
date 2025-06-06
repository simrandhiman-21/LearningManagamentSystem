package com.example.exam_service.security;

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
                        .requestMatchers("/api/quizzes").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/quizzes/{quizId}").hasAnyAuthority("ROLE_ADMIN", "ROLE_INSTRUCTOR", "ROLE_STUDENT")
                        .requestMatchers("/api/quizzes/submit").hasAuthority("ROLE_STUDENT")
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());
        return http.build();
    }
}
