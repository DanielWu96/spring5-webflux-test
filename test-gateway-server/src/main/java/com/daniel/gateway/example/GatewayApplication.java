package com.daniel.gateway.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: daniel
 * @date: 2020/5/15 11:15
 */
@SpringBootApplication
@Slf4j
public class GatewayApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("gateway start success");
    }
}
