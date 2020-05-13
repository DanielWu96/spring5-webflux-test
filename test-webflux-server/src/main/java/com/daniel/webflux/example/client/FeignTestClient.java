package com.daniel.webflux.example.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 非reactive的feign调用（老版本）
 *
 * @Author: daniel
 * @date: 2020/5/12 15:27
 */
//@FeignClient(url = "https://localhost:8003", name = "test")
@FeignClient(value = "test-webflux-client", name = "test")
public interface FeignTestClient {
    @GetMapping(value = "testclient/users", headers = {"Authorization=daniel", "Content-Type=application/json;charset=UTF-8"})
    String users(@RequestParam(value = "phone") String value);
}
