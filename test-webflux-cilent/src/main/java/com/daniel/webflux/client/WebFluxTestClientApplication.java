package com.daniel.webflux.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author: daniel
 * @date: 2020/5/13 16:12
 */
@SpringBootApplication
@EnableEurekaClient
public class WebFluxTestClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebFluxTestClientApplication.class,args);
    }
}
