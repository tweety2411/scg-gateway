package com.heypli.scg.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class TestService {

//    @CircuitBreaker(name = "test", fallbackMethod = "fallbackGet")
    public String test() {
        return "hi";
    }

//    @CircuitBreaker(name="test", fallbackMethod = "fallbackGet")
    public String fail() {
        throw new RuntimeException("Failed");
    }

    private String fallbackGet(Throwable  t) {
        return "FallBack error Message : " + t.getMessage();
    }

}
