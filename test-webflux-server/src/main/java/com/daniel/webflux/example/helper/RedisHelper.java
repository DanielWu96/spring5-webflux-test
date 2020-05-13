package com.daniel.webflux.example.helper;

import com.daniel.webflux.example.constants.RedisConstants;
import com.daniel.webflux.example.exception.RedisTimeoutException;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @Author: daniel
 * @date: 2020/4/10 14:26
 */
public class RedisHelper {

    public static Mono<String> getStringValue(ReactiveRedisTemplate template, String key, String reqId, String functionName) {
        return template.opsForValue()
                .get(key)
                .timeout(Duration.ofMillis(RedisConstants.REDIS_QUERY_TIMEOUT_MILLS), Mono.error(new RedisTimeoutException(reqId, functionName)))
                .defaultIfEmpty("");
    }

    public static Mono<String> getHashValue(ReactiveRedisTemplate template, String key, String field, String reqId, String functionName) {
        return template.opsForHash()
                .get(key, String.valueOf(field))
                .timeout(Duration.ofMillis(RedisConstants.REDIS_QUERY_TIMEOUT_MILLS), Mono.error(new RedisTimeoutException(reqId, functionName)))
                .defaultIfEmpty("");
    }

    public static Mono<Boolean> expire(ReactiveRedisTemplate template, String key,long timeout, String reqId, String functionName){
        return template.expire(key, Duration.ofSeconds(timeout))
                .timeout(Duration.ofMillis(RedisConstants.REDIS_QUERY_TIMEOUT_MILLS), Mono.error(new RedisTimeoutException(reqId, functionName)))
                .defaultIfEmpty(false);
    }

    public static Mono<Long> incrOne(ReactiveRedisTemplate template, String key,String reqId, String functionName){
        return template.opsForValue()
                .increment(key)
                .timeout(Duration.ofMillis(RedisConstants.REDIS_QUERY_TIMEOUT_MILLS), Mono.error(new RedisTimeoutException(reqId, functionName)))
                .defaultIfEmpty(0);
    }
}
