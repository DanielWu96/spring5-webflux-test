package com.daniel.webflux.example.controller;

import com.daniel.webflux.example.annotations.Limit;
import com.daniel.webflux.example.client.FeignTestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @Author: daniel
 * @date: 2020/3/31 13:04
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class FirstController{

    FeignTestClient feignTestClient;

    @Autowired
    public void setFeignTestClient(FeignTestClient feignTestClient) {
        this.feignTestClient = feignTestClient;
    }


    @GetMapping("/getOne")
    @Limit(value=10)
    public Mono<String> getOne() {
        log.info("处理接口");
        return Mono.just("ok");
    }

    @GetMapping("/testFeign")
    public Mono<Map<String,Object>> testFeign(String value) {
        return Mono.create(monoSink->{
            Map<String,Object> result = feignTestClient.users(value);
            monoSink.success(result);
        });
    }
}
