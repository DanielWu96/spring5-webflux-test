package com.daniel.webflux.example.filter;


import com.daniel.webflux.example.annotations.Limit;
import com.daniel.webflux.example.helper.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import reactor.core.publisher.Mono;


/**
 * @Author: daniel
 * @date: 2020/4/29 18:36
 */
@Service
@Slf4j
public class LimitService {
    @Value("${flow.limit.interface.key:LIMIT_}")
    private String SINGLE_REDIS_PREFIX;
    @Value("${flow.limit.tatal.key:DEVICE_CONVERSION_LIMIT}")
    private String TOTAL_REDIS_KEY;
    @Value("${flow.limit.tatal.count:8000}")
    private int totalLimit;

    ReactiveRedisTemplate reactiveRedisTemplate;

    @Autowired
    @Qualifier("idfa2uidRedisTemplate")
    public void setIdfaUidRedisClient(ReactiveRedisTemplate idfaUidRedisClient) {
        this.reactiveRedisTemplate = idfaUidRedisClient;
    }


    public Mono<Boolean> isLimiting(Object e, String reqId) {
        HandlerMethod handlerMethod = (HandlerMethod) e;
        Limit limit = handlerMethod.getMethodAnnotation(Limit.class);
        if (limit == null) {
            return Mono.just(false);
        }
        //针对接口的key
        String key = SINGLE_REDIS_PREFIX + ((HandlerMethod) e).getMethod().getName();
        return Mono.zip(singleLimit(key, reqId, limit), totalLimit(reqId))
                .map(tuple2 -> {
                    Boolean b1 = tuple2.getT1();
                    Boolean b2 = tuple2.getT2();
                    if (b1 || b2)
                        return true;
                    return false;
                });
    }

    /**
     * 接口限制
     *
     * @param key
     * @param reqId
     * @param limit
     * @return
     */
    private Mono<Boolean> singleLimit(String key, String reqId, Limit limit) {
        return RedisHelper.incrOne(reactiveRedisTemplate, key, reqId, "singleLimit")
                .flatMap(count -> {
                    log.info("接口:{},当前秒内调用次数:{},接口限制次数：{}",key,count,limit.value());
                    if (count == 0)
                        return Mono.just(true);
                    //第一次赋值，设置失效时间
                    if (count == 1) {
                        return RedisHelper.expire(reactiveRedisTemplate, key, 1, reqId, "singleLimit")
                                .map(success->!success);
                    }
                    //判断是否超过限制
                    if (count > limit.value()) {
                        return Mono.just(true);
                    }
                    return Mono.just(false);
                });
    }

    /**
     * 总请求限制
     *
     * @param reqId
     * @return
     */
    private Mono<Boolean> totalLimit(String reqId) {
        return RedisHelper.incrOne(reactiveRedisTemplate, TOTAL_REDIS_KEY, reqId, "totalLimit")
                .flatMap(count -> {
                    log.info("项目当前秒内调用次数:{},总限制次数{}",count,totalLimit);
                    if (count == 0)
                        return Mono.just(true);
                    //第一次赋值，设置失效时间
                    if (count == 1) {
                        return RedisHelper.expire(reactiveRedisTemplate, TOTAL_REDIS_KEY, 1, reqId, "totalLimit")
                                .map(success->!success);
                    }
                    //判断是否超过限制
                    if (count > totalLimit) {
                        return Mono.just(true);
                    }
                    return Mono.just(false);
                });
    }
}
