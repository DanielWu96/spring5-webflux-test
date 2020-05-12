package com.daniel.webflux.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @Author: daniel
 * @date: 2020/3/31 13:04
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.daniel.webflux.example.client"})
public class WebFluxTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebFluxTestApplication.class,args);
    }

}
