package com.akzv.resexecutor.resilientExecutor;

import io.github.resilience4j.circuitbreaker.*;

public class CircuitBreakerFactory {
    
    public static CircuitBreaker createDefaultBreaker(String name) {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(java.time.Duration.ofSeconds(10))
                .permittedNumberOfCallsInHalfOpenState(3)
                .slidingWindowSize(10)
                .build();

        return CircuitBreaker.of(name, config);
    }
}