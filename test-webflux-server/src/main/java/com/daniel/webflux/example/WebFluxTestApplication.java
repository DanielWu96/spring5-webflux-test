package com.daniel.webflux.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * @Author: daniel
 * @date: 2020/3/31 13:04
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class WebFluxTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebFluxTestApplication.class,args);
    }

}
