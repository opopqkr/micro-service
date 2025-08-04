package com.study.user_service.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Circuit Breaker </br>
 * <p>
 * - <a href="https://martinfowler.com/bliki/CircuitBreaker.html">Martin fowler wiki</a>
 * - 장애가 발생하는 서비스에 반복적인 호출이 되지 못하게 차단
 * - 특정 서비스가 정상적으로 동작하지 않을 경우 다른 기능으로 대체 수행 (장애 회피)
 * - 정상적일 경우 Circuit Breaker Close
 * - 장애가 발생할 경우 Circuit Breaker Open
 */
@Configuration
public class Resilience4JConfig {

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(4) // CircuitBreaker Open 실패율 임계치 (failure rate threshold percentage)
                .waitDurationInOpenState(Duration.ofMillis(1000)) // CircuitBreaker Open 유지 시간, 이 후 half-open 상태 (default 60초)
                // CircuitBreaker 상태를 평가하기 위한 타입 및 크기 지정, 실패율 혹은 성공률 등을 계산하기 위함.
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED) // default count
                .slidingWindowSize(2) // default 100
                .build();

        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom() // supplier time limit 지정
                .timeoutDuration(Duration.ofSeconds(4)) // default 1초
                .build();

        return resilience4JCircuitBreakerFactory -> resilience4JCircuitBreakerFactory.configureDefault(
                id -> new Resilience4JConfigBuilder(id)
                        .circuitBreakerConfig(circuitBreakerConfig)
                        .timeLimiterConfig(timeLimiterConfig)
                        .build()
        );
    }
}
