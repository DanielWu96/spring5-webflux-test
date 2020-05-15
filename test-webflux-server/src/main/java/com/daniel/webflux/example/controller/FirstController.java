package com.daniel.webflux.example.controller;

import com.daniel.webflux.example.annotations.Limit;
import com.daniel.webflux.example.client.WebfluxTestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FirstController {

    WebfluxTestClient webfluxTestClient;

    @Autowired
    public void setFeignTestClient(WebfluxTestClient webfluxTestClient) {

        this.webfluxTestClient=webfluxTestClient;
    }

    @GetMapping("/testLimit")
    @Limit(value = 10)
    public Mono<String> testLimit() {
        log.info("接口没有受限");
        return Mono.just("ok");
    }

    @GetMapping("/getOne")
    public Mono<String> getOne() {
        log.info("处理接口");
        return Mono.just("ok");
    }

    @GetMapping("/testReactiveWebClient")
    public Mono<String> testReactiveWebClient(String value){
        return webfluxTestClient.users(value);
    }
}
