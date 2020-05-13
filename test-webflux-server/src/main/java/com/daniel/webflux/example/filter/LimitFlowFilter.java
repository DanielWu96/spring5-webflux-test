package com.daniel.webflux.example.filter;


import com.alibaba.fastjson.JSONObject;
import com.daniel.webflux.example.common.CommonRespEnum;
import com.daniel.webflux.example.common.RequestContext;
import com.daniel.webflux.example.domain.ApiResult;
import com.daniel.webflux.example.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * 限流过滤器
 *
 * @Author: daniel
 * @date: 2020/4/1 13:58
 */
@Slf4j
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@Order(value = 1)
public class LimitFlowFilter implements WebFilter {

    LimitService limitService;

    RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    public void setLimitService(LimitService limitService) {
        this.limitService = limitService;
    }

    @Autowired
    public void setRequestMappingHandlerMapping(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        long startTime=System.currentTimeMillis();
        return Mono.subscriberContext()
                .flatMap(context -> {
                    RequestContext requestContext = context.get(RequestContext.class);
                    String uri = requestContext.getUri();
                    String reqId = requestContext.getReqId();
                    log.info("filter start handle request, reqId:{}, uri:{}", reqId, uri);
                    return requestMappingHandlerMapping.getHandler(exchange)
                            .defaultIfEmpty(Optional.empty())
                            .flatMap(object -> {
                                ServerHttpResponse response = exchange.getResponse();
                                response.setStatusCode(HttpStatus.OK);
                                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                                if (object instanceof Optional) {
                                    //没找到接口 404
                                    return response.writeWith(Mono.just(response.bufferFactory()
                                            .wrap(JSONObject.toJSONString(ApiResult.error(new BaseException(CommonRespEnum.NOT_FOUND))).getBytes())));
                                }

                                HandlerMethod handlerMethod=(HandlerMethod) object;
                                return limitService.isLimiting(handlerMethod, reqId)
                                        .flatMap(limit -> {
                                            if (limit) {
                                                return response.writeWith(Mono.just(response.bufferFactory()
                                                        .wrap(JSONObject.toJSONString(ApiResult.error(new BaseException(CommonRespEnum.SERVER_BUSY))).getBytes())));
                                            }
                                            return chain.filter(exchange)
                                                    .doFinally(signalType -> log.info("接口{}耗时{}ms",handlerMethod.getMethod().getName(),System.currentTimeMillis()-startTime));
                                        });
                            });
                });
    }
}
