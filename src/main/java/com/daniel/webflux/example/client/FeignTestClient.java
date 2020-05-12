package com.daniel.webflux.example.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author: daniel
 * @date: 2020/5/12 15:27
 */
@FeignClient(url = "https://localhost:8081", name = "test")
public interface FeignTestClient {
    @GetMapping(value = "test/users", headers = {"Authorization=daniel","Content-Type=application/json;charset=UTF-8"} )
    Map<String, Object> users(@RequestParam(value="phone") String value);
}
