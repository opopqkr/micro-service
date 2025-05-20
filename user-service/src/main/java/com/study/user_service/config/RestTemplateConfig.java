package com.study.user_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced // 다른 마이크로 서비스 호출 시, discovery server 에 등록되어 있는 micro-service 명으로 호출 가능
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
