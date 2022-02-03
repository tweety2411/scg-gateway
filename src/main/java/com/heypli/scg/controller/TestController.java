package com.heypli.scg.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.Getter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Random;

@RestController
public class TestController {

    @CircuitBreaker(name = "test", fallbackMethod = "/fallback")
    @GetMapping("/test")
    public String test() {
        randomException();
        return "";
    }

    private void randomException() {
        int randomInt = new Random().nextInt(10);

        if(randomInt <= 7) {
            throw new RuntimeException("failed");
        }
    }


    @Order(-2)
    @GetMapping("/fallback")
    public Mono<Void> fallbackGet(ServerWebExchange exchange) {
        Throwable t = exchange.getAttribute(ServerWebExchangeUtils.CIRCUITBREAKER_EXECUTION_EXCEPTION_ATTR);
        System.out.println("#################");
        System.out.println(t.getClass());
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR");
    }
}
