package com.learning.userservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
public class UserserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
		log.info("UserService is up and running ...");
	}
}
