package com.study.apigateway_service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

// @Configuration
public class FilterConfig {

    // @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/first-service/**") // application 설정의 spring.cloud.gateway.routes.predicates 설정과 동일
                        .filters(f -> f
                                .addRequestHeader("first-request", "first-request-header1")
                                .addResponseHeader("first-response", "first-response-header1"))
                        .uri("http://localhost:8081")) // application 설정의 spring.cloud.gateway.routes.uri 설정과 동일
                .route(r -> r
                        .path("/second-service/**")
                        .filters(f -> f
                                .addRequestHeader("second-request", "second-request-header1")
                                .addResponseHeader("second-response", "second-response-header1"))
                        .uri("http://localhost:8082"))
                .build();
    }
}
