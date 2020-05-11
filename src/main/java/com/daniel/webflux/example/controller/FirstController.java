package com.daniel.webflux.example.controller;

import com.daniel.webflux.example.annotations.Limit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @Author: daniel
 * @date: 2020/3/31 13:04
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class FirstController{

    @GetMapping("/getOne")
    @Limit(value=10)
    public Mono<String> getOne() {
        log.info("处理接口");
        return Mono.just("ok");
    }

    @GetMapping("/getTwo")
    public Mono<String> getTwo() {
        return Mono.just("ok");
    }
}
