package com.daniel.webflux.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @Author: daniel
 * @date: 2020/5/13 16:13
 */
@RestController
@Slf4j
@RequestMapping("testclient")
public class TestController {
    @GetMapping("users")
    public Mono<String> users(@RequestParam("phone") String phone){
        return Mono.just("webfluxclient receive value "+phone +" success");
    }
}
