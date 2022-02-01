package com.heypli.scg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
public class ScgGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScgGatewayApplication.class);
    }
}
