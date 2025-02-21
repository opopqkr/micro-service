package com.study.user_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class UserServiceApplication {

	public static void main(String[] args) {
		log.debug("### user-service start ###");
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
