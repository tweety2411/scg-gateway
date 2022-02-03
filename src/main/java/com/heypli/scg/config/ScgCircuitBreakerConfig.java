package com.heypli.scg.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.consumer.CircularEventConsumer;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import io.vavr.collection.Seq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
public class ScgCircuitBreakerConfig {

    @Bean
    public ReactiveResilience4JCircuitBreakerFactory defaultCustomizer(CircuitBreakerRegistry registry,
                                                                                   TimeLimiterRegistry timeLimiterRegistry) {
        ReactiveResilience4JCircuitBreakerFactory factory = new ReactiveResilience4JCircuitBreakerFactory(registry, timeLimiterRegistry);

        Seq<CircuitBreaker> circuitBreakerSeq = registry.getAllCircuitBreakers();

        for(CircuitBreaker circuit : circuitBreakerSeq) {
            log.error("circuitName : " + circuit.getName());
        }

        Seq<TimeLimiter> allTimeLimiters = timeLimiterRegistry.getAllTimeLimiters();

        for(TimeLimiter timeLimiter: allTimeLimiters) {
            String timeLimiterName = timeLimiter.getName();
            log.error("timeLimiterName : " + timeLimiterName);
            factory.configure(resilience4JConfigBuilder -> resilience4JConfigBuilder.timeLimiterConfig(timeLimiterRegistry.timeLimiter(timeLimiterName).getTimeLimiterConfig())
                .circuitBreakerConfig(registry.circuitBreaker(timeLimiterName).getCircuitBreakerConfig()), timeLimiterName);
        }

        return factory;
    }
}
