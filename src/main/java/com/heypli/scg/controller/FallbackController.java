package com.heypli.scg.controller;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public Mono<Void> fallback(ServerWebExchange exchange) {
        Throwable t = exchange.getAttribute(ServerWebExchangeUtils.CIRCUITBREAKER_EXECUTION_EXCEPTION_ATTR);
        if(t instanceof TimeoutException) {
            throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, "Service Timeout");

        } else if (t instanceof CallNotPermittedException) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service is not available");
        }
        return null;
    }
}
